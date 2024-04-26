package org.example.rentapplicationbe.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.rentapplicationbe.config.JwtTokenUtil;
import org.example.rentapplicationbe.model.Entity.Account;
import org.example.rentapplicationbe.model.Entity.Role;
import org.example.rentapplicationbe.model.dto.AccountDTO;
import org.example.rentapplicationbe.model.dto.AccountUserDTO;
import org.example.rentapplicationbe.model.dto.ApiResponse;
import org.example.rentapplicationbe.model.dto.ChangePasswordUser;
import org.example.rentapplicationbe.service.IAccountService;
import org.example.rentapplicationbe.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/account")
public class AccountRestController {

    @Autowired
    private IAccountService iAccountService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping("/login")
    public ResponseEntity<Object> loginAccount(HttpServletResponse response, @RequestBody
    AccountDTO accountDTO) { // đăng nhập
        ApiResponse<Account> apiResponse = new ApiResponse<>(); // tạo apiResponse
        try {
            String token = iAccountService.login(accountDTO.getNameAccount(), accountDTO.getPassword()); // đăng nhập
            Account user = iAccountService.findAccountByAccountName(accountDTO.getNameAccount()).get();// tìm user theo tên tài khoản
            apiResponse.setToken(token); // set token
            apiResponse.setDataRes(user); // set user
//            Cookie jwtCookie = new Cookie("JWT",token);
//            jwtCookie.setHttpOnly(true);
//            jwtCookie.setSecure(true);
//            jwtCookie.setPath("/");
//            jwtCookie.setMaxAge(7 * 24 * 60 * 60);
//            response.addCookie(jwtCookie);

            return ResponseEntity.ok(apiResponse); // trả về apiResponse

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // trả về lỗi
        }
    }


    @PostMapping("/registerr/user")
    public ResponseEntity<String> createAccountUser(@RequestBody Account account) { // tạo tài khoản
        Role role = roleService.findById(2L);
        account.setRole(role); // 2 là role user
        System.out.println(account.getFull_name());
        account.setPassword(passwordEncoder.encode(account.getPassword())); // mã hóa password
        iAccountService.save(account); // lưu user vào database
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/registerr/host")
    public ResponseEntity<String> createAccountHost(@RequestBody Account account) { // tạo tài khoản
        Role role = roleService.findById(3L);
        account.setRole(role); // 2 là role user
        System.out.println(account.getFull_name());
        account.setPassword(passwordEncoder.encode(account.getPassword())); // mã hóa password
        iAccountService.save(account); // lưu user vào database
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordUser request, @RequestHeader("Authorization") String tokenHeader
    ) {
        String token = tokenHeader.substring(7);
        String username1 = jwtTokenUtil.extractUserName(token);
        iAccountService.findAccountByAccountName(username1);
        iAccountService.changePassword(username1, request);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/checkUserName")
    private ResponseEntity<List<Account>> checkUserName(@RequestParam String userName) { // kiểm tra tên tài khoản đã tồn tại chưa
        List<Account> list = iAccountService.checkUserName(userName); // kiểm tra tên tài khoản đã tồn tại chưa
        return new ResponseEntity<>(list, HttpStatus.OK); // trả về list user
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findAccountById(@PathVariable Long id){
        Optional<Account> account = iAccountService.findById(id);
        if (!account.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    @PutMapping("/update/infor/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account, @RequestHeader("Authorization") String tokenHeader){
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Account> accountUpdate = iAccountService.findById(id);
        if (!accountUpdate.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Account existingAccount = accountUpdate.get();
        existingAccount.setAvatar(account.getAvatar());
        existingAccount.setFull_name(account.getFull_name());
        existingAccount.setAddress(account.getAddress());

        iAccountService.save(existingAccount);

        return new ResponseEntity<>(existingAccount, HttpStatus.OK);
    }
}

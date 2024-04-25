package org.example.rentapplicationbe.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.rentapplicationbe.config.JwtTokenUtil;
import org.example.rentapplicationbe.model.Entity.Account;
import org.example.rentapplicationbe.model.Entity.Role;
import org.example.rentapplicationbe.model.dto.AccountDTO;
import org.example.rentapplicationbe.model.dto.ApiResponse;
import org.example.rentapplicationbe.model.dto.ChangePasswordUser;
import org.example.rentapplicationbe.service.IAccountService;
import org.example.rentapplicationbe.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/registerr")
    private ResponseEntity<String> createAccount(@RequestBody Account account) { // tạo tài khoản
        Role role = roleService.findById(2L);
        account.setRole(role); // 2 là role user
        System.out.println(account.getFull_name());
        account.setPassword(passwordEncoder.encode(account.getPassword())); // mã hóa password
        iAccountService.save(account); // lưu user vào database
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

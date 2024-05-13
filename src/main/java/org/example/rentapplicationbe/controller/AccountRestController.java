package org.example.rentapplicationbe.controller;

import org.example.rentapplicationbe.config.service.JwtResponse;
import org.example.rentapplicationbe.config.service.JwtService;
import org.example.rentapplicationbe.config.service.UserService;
import org.example.rentapplicationbe.model.Entity.Account;
import org.example.rentapplicationbe.model.Entity.Role;
import org.example.rentapplicationbe.model.dto.ChangePasswordUser;
import org.example.rentapplicationbe.service.IAccountService;
import org.example.rentapplicationbe.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account user) {
//        String a = "aa";
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        Account user = iAccountService.findAccountByAccountName(accountDTO.getNameAccount()).get();// tìm user theo tên tài khoản

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account currentUser = userService.findByUsername(user.getUsername());
        System.out.println("kk " + currentUser);
        String avatarUrl = currentUser.getAvatar();
        System.out.println(avatarUrl);

        return ResponseEntity.ok(new JwtResponse(currentUser.getId(), jwt, userDetails.getUsername(), userDetails.getUsername(), userDetails.getAuthorities(),avatarUrl));
    }

    @GetMapping("/getInfo")
    public ResponseEntity<?> getInfo(@RequestHeader("Authorization") String token) {
        String newToken = token.substring(7);
        String userName = jwtService.getUsernameFromJwtToken(newToken);
        System.out.println(userName);
        return ResponseEntity.ok(userName);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutSuccessful(@RequestHeader("Authorization") String token) {
        String newToken = token.substring(7);
        jwtService.addToBlackList(newToken);
        return ResponseEntity.ok("ok dang xuat");
    }


    @PostMapping("/registerr/user")
    public ResponseEntity<String> createAccountUser(@RequestBody Account account) { // tạo tài khoản
        Role role = roleService.findById(1L);
        account.setRole(role); // 2 là role user
        System.out.println(account.getFull_name());
        account.setPassword(passwordEncoder.encode(account.getPassword())); // mã hóa password
        iAccountService.save(account); // lưu user vào database
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/registerr/host")
    public ResponseEntity<String> createAccountHost(@RequestBody Account account) { // tạo tài khoản
        Role role = roleService.findById(2L);
        account.setRole(role); // 2 là role user
        System.out.println(account.getFull_name());
        account.setPassword(passwordEncoder.encode(account.getPassword())); // mã hóa password
        iAccountService.save(account); // lưu user vào database
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @PutMapping("/change-password")
//    public ResponseEntity<String> changePassword(
//            @RequestBody ChangePasswordUser request, @RequestHeader("Authorization") String tokenHeader
//    ) {
//        String token = tokenHeader.substring(7);
//        String username1 = jwtService.getUsernameFromJwtToken(token);
//        iAccountService.findAccountByAccountName(username1);
//        iAccountService.changePassword(username1, request);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/checkUserName")
    private ResponseEntity<List<Account>> checkUserName(@RequestParam String userName) { // kiểm tra tên tài khoản đã tồn tại chưa
        List<Account> list = iAccountService.checkUserName(userName); // kiểm tra tên tài khoản đã tồn tại chưa
        return new ResponseEntity<>(list, HttpStatus.OK); // trả về list user
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findAccountById(@PathVariable Long id) {
        Optional<Account> account = iAccountService.findById(id);
        if (!account.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    @PutMapping("/update/infor/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        iAccountService.save(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
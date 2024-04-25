package org.example.rentapplicationbe.controller.changepassword;

import org.example.rentapplicationbe.config.JwtTokenUtil;
import org.example.rentapplicationbe.model.dto.ChangePasswordUser;
import org.example.rentapplicationbe.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("")
public class ChangePasswordController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private IAccountService iAccountService;

    @PutMapping("/users/change-password")
    public ResponseEntity<String> changePasswordUser(
            @RequestBody ChangePasswordUser request, @RequestHeader("Authorization") String tokenHeader
    ) {
        String token = tokenHeader.substring(7);
        String username = jwtTokenUtil.extractUserName(token);
        iAccountService.findAccountByAccountName(username);
        iAccountService.changePassword(username, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/host/change-password")
    public ResponseEntity<String> changePasswordHost(
            @RequestBody ChangePasswordUser request, @RequestHeader("Authorization") String tokenHeader
    ) {
        String token = tokenHeader.substring(7);
        String username = jwtTokenUtil.extractUserName(token);
        iAccountService.findAccountByAccountName(username);
        iAccountService.changePassword(username, request);
        return ResponseEntity.ok().build();
    }
}

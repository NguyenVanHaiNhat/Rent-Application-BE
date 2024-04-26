package org.example.rentapplicationbe.controller.changepassword;

import org.example.rentapplicationbe.config.service.JwtService;
import org.example.rentapplicationbe.model.dto.ChangePasswordUser;
import org.example.rentapplicationbe.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("")
public class ChangePasswordController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IAccountService iAccountService;

    @PreAuthorize("hasAnyRole('HOST','USER')")
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordUser request, @RequestHeader("Authorization") String tokenHeader
    ) {
        String token = tokenHeader.substring(7);
        String username = jwtService.getUsernameFromJwtToken(token);
        iAccountService.findAccountByAccountName(username);
        iAccountService.changePassword(username, request);
        return ResponseEntity.ok().build();
    }
}

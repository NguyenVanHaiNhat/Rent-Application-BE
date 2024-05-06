
package org.example.rentapplicationbe.controller;

import org.example.rentapplicationbe.config.service.JwtService;
import org.example.rentapplicationbe.model.dto.AccountUserDTO;
import org.example.rentapplicationbe.service.AccountService;
import org.example.rentapplicationbe.services.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/account-dto")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private HostService hostService;


    @GetMapping("/user")
    public ResponseEntity<List<AccountUserDTO>> getAllUser(@RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(7);
        String username1 = jwtService.getUsernameFromJwtToken(token);
        accountService.findAccountByAccountName(username1);
        List<AccountUserDTO> accountUsers = accountService.findAllUser(username1);
        return ResponseEntity.ok(accountUsers);
    }

    @PutMapping("/{id}/status/{newStatus}")
    public ResponseEntity<String>updateStatus(@PathVariable Long id, @PathVariable String newStatus){
        hostService.updateAccountStatus(id,newStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

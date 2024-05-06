
package org.example.rentapplicationbe.controller;

import org.example.rentapplicationbe.config.service.JwtService;
import org.example.rentapplicationbe.model.dto.AccountUserDTO;
import org.example.rentapplicationbe.model.dto.HostDtoDetail;
import org.example.rentapplicationbe.model.dto.UserDetail;
import org.example.rentapplicationbe.service.AccountService;
import org.example.rentapplicationbe.services.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDetail> getAllUserDetail(@PathVariable Long id) {
        Optional<UserDetail> userDetail = accountService.findByIdUserDetail(id);
        if (!userDetail.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDetail.get(),HttpStatus.OK);
    }

    @PutMapping("/{id}/status/{newStatus}")
    public ResponseEntity<String>updateStatus(@PathVariable Long id, @PathVariable String newStatus){
        hostService.updateAccountStatus(id,newStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

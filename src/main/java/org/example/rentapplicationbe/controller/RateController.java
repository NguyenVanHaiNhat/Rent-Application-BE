package org.example.rentapplicationbe.controller;

import org.example.rentapplicationbe.config.service.JwtService;
import org.example.rentapplicationbe.model.Entity.Rate;
import org.example.rentapplicationbe.model.dto.RateDTO;
import org.example.rentapplicationbe.service.IAccountService;
import org.example.rentapplicationbe.services.rate.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("api/rate")
public class RateController {
    @Autowired
    private IRateService iRateService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IAccountService iAccountService;

    @PostMapping("/create/{id}")
    public ResponseEntity<Rate> CreateRate( @RequestHeader("Authorization")String tokenHeader,@PathVariable Long id,@RequestParam Long id_account, @RequestBody Rate rate) {
        String token = tokenHeader.substring(7);
        String username1 = jwtService.getUsernameFromJwtToken(token);
       // iAccountService.findAccountByAccountName(username1);
        iRateService.createRate(id,id_account, rate,username1);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<RateDTO>> findAllRate(@PathVariable Long id) {
        List<RateDTO>rates = iRateService.findAllRateByIdHouse(id);
        return new ResponseEntity<>(rates, HttpStatus.OK);

    }
}
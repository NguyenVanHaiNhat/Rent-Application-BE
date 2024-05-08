package org.example.rentapplicationbe.controller;

import org.example.rentapplicationbe.config.service.JwtService;
import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.repository.AccountRepository;
import org.example.rentapplicationbe.service.IAccountService;
import org.example.rentapplicationbe.services.house.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping()

public class HomeController {
    @Autowired
    private IHouseService iHouseService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IAccountService iAccountService;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping()
    public ResponseEntity<List<House>> findAll(
            @RequestParam(required = false, defaultValue = "") Integer bedrooms,
            @RequestParam(required = false, defaultValue = "") Integer bathrooms,
            @RequestParam(required = false, defaultValue = "") String address,
            @RequestParam(required = false, defaultValue = "") Long price,
            @RequestParam(required = false, defaultValue = "") LocalDate checkInDate,
            @RequestParam(required = false, defaultValue = "") LocalDate checkOutDate)
    {
        List<House> houses =iHouseService.searchAll(bedrooms, bathrooms, address, price,checkInDate,checkOutDate);
        return ResponseEntity.ok(houses);
    }
    @GetMapping("/top5")
    public ResponseEntity<List<House>> top5House() {
        List<House> top5MostBookedHouses = iHouseService.findTop5MostBookedHouses();
        System.out.println(top5MostBookedHouses);
        if (top5MostBookedHouses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(top5MostBookedHouses);
    }
}

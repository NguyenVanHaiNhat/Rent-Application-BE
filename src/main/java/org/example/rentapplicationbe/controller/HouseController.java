package org.example.rentapplicationbe.controller;

import org.example.rentapplicationbe.config.service.JwtService;
import org.example.rentapplicationbe.model.Entity.Account;
import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.model.dto.HouseDetail;
import org.example.rentapplicationbe.service.IAccountService;
import org.example.rentapplicationbe.services.house.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/house")
public class HouseController {
    @Autowired
    private IHouseService iHouseService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IAccountService iAccountService;

    @GetMapping("/owner/{id}")
    public ResponseEntity<List<House>> findAllHouse(@PathVariable Long id, @RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(7);
        String username1 = jwtService.getUsernameFromJwtToken(token);
        iAccountService.findAccountByAccountName(username1);
        List<House> houses = iHouseService.findByIdDetailHouse(id);
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }



    @GetMapping("/ownerRented/{id}")
    public ResponseEntity<List<House>> findAllRented(@PathVariable Long id) {
        List<House> houses = iHouseService.findRentedHousesByOwnerId(id);
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }
    @GetMapping("/ownerMaintenance/{id}")
    public ResponseEntity<List<House>> findAllHouseMaintenance(@PathVariable Long id) {
        List<House> houses = iHouseService.findMaintenanceHousesByOwnerId(id);
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }
    @GetMapping("/ownerAvailable/{id}")
    public ResponseEntity<List<House>> findAllHouseAvailable(@PathVariable Long id) {
        List<House> houses = iHouseService.findAvailableHousesByOwnerId(id);
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<House> getHouseById(@PathVariable Long id) {
        Optional<House> houseOptional = iHouseService.findById(id);
        if (!houseOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(houseOptional.get(), HttpStatus.OK);
    }
    @GetMapping("/detail/image/{id}")
    public ResponseEntity<HouseDetail> getHouseByIdImages(@PathVariable Long id) {
        Optional<HouseDetail> houseImage = iHouseService.findByIdHouseImage(id);
        if (!houseImage.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(houseImage.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<House> editHouseById(@RequestBody House house, @RequestHeader("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(7);
        String username1 = jwtService.getUsernameFromJwtToken(token);
        Optional<Account> accountOptional = iAccountService.findAccountByAccountName(username1);
        if (!accountOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Account account = accountOptional.get();
        house.setAccount(account);
        House house1 = iHouseService.save(house);
        return new ResponseEntity<>(house1, HttpStatus.OK);
    }
}
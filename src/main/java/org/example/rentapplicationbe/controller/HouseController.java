package org.example.rentapplicationbe.controller;

import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.services.house.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/house")
public class HouseController {
    @Autowired
    private IHouseService iHouseService;

    @GetMapping("/owner/{id}")
    public ResponseEntity<List<House>> findAllHouse(@PathVariable Long id) {
        List<House> houses = iHouseService.findByIdDetailHouse(id);
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }
}

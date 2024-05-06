package org.example.rentapplicationbe.controller;

import org.example.rentapplicationbe.model.Entity.Image;
import org.example.rentapplicationbe.services.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    private ImageService imageService;
    @GetMapping
    public ResponseEntity<List<Image>> getAll(){
        return new ResponseEntity<>(imageService.getAllImage(), HttpStatus.OK);
    }

    @PostMapping("/{houseId}")
    public ResponseEntity<Image> createImage(@RequestBody Image image, @PathVariable("houseId") Long houseId) {
        Image savedImage = imageService.save(image, houseId);
        return new ResponseEntity<>(savedImage, HttpStatus.CREATED);
    }
}

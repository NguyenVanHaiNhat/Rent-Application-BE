package org.example.rentapplicationbe.controller;

import org.example.rentapplicationbe.model.DTO.HostDtoDetail;
import org.example.rentapplicationbe.services.IHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/host")
public class HostController {
    @Autowired
    private IHostService iHostService;
    @GetMapping("/dto")
    public ResponseEntity<List<HostDtoDetail>>findAllHost(){
        List<HostDtoDetail>hostDtoDetails= iHostService.getHostInfor();
        return new ResponseEntity<>(hostDtoDetails,)
    }
}

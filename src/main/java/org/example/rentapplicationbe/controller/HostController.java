package org.example.rentapplicationbe.controller;

import org.example.rentapplicationbe.model.dto.HostDtoDetail;
import org.example.rentapplicationbe.services.IHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/host")
public class HostController {
    @Autowired
    private IHostService iHostService;

    @GetMapping("/dto")
    public ResponseEntity<List<HostDtoDetail>> findAllHost() {
        List<HostDtoDetail> hostDtoDetails = iHostService.getHostInfor();
        return new ResponseEntity<>(hostDtoDetails, HttpStatus.OK);
    }
    @GetMapping("/dto/{id}")
    public ResponseEntity<HostDtoDetail> getAllDetailHost(@PathVariable Long id) {
        Optional<HostDtoDetail> hostDtoDetails = iHostService.findByIdHost(id);
        if (!hostDtoDetails.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hostDtoDetails.get(),HttpStatus.OK);
    }
    @PutMapping("/{id}/status/{newStatus}")
    public ResponseEntity<String>updateStatus(@PathVariable Long id, @PathVariable String newStatus){
        iHostService.updateAccountStatus(id,newStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package org.example.rentapplicationbe.controller;

import org.example.rentapplicationbe.config.service.JwtService;
import org.example.rentapplicationbe.model.Entity.Account;
import org.example.rentapplicationbe.model.Entity.Bookings;
import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.model.dto.BookHouseDTO;
import org.example.rentapplicationbe.model.dto.HistoryBooking;
import org.example.rentapplicationbe.model.dto.HostDtoDetail;
import org.example.rentapplicationbe.repository.IBookingRepository;
import org.example.rentapplicationbe.repository.IHouseRepository;
import org.example.rentapplicationbe.service.IAccountService;
import org.example.rentapplicationbe.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("")
public class BookingController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IAccountService iAccountService;
    @Autowired
    private IBookingService iBookingService;
    @Autowired
    private IBookingRepository iBookingRepository;
    @PostMapping("/bookAHouse")
    public ResponseEntity<Object> createBookAHouse(@RequestBody BookHouseDTO bookHouseDTO, @RequestHeader("Authorization") String tokenHeader) {
        try {
            // Kiểm tra ngày đặt không trùng nhau và start_date không trước hoặc bằng end_date
            if (bookHouseDTO.getStart_date().isAfter(bookHouseDTO.getEnd_date())) {
                return new ResponseEntity<>("Start date must be before end date and they cannot be the same", HttpStatus.BAD_REQUEST);
            }

            LocalDate currentDate = LocalDate.now();
            if (bookHouseDTO.getStart_date().isBefore(currentDate)) {
                return new ResponseEntity<>("Start date must be after today", HttpStatus.BAD_REQUEST);
            }
            Long idHouse = bookHouseDTO.getIdHouse();
            if (idHouse == null) {
                return new ResponseEntity<>("IdHouse is required", HttpStatus.BAD_REQUEST);
            }
            String token = tokenHeader.substring(7);
            String username = jwtService.getUsernameFromJwtToken(token);
            bookHouseDTO.setIdAccount(iAccountService.findAccountByAccountName(username).get().getId());
            iBookingService.save(bookHouseDTO);
            return new ResponseEntity<>(bookHouseDTO, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to save booking: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelBooking (@PathVariable Long id,@RequestHeader ("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(7);
        String username = jwtService.getUsernameFromJwtToken(token);
        if (iBookingRepository.findById(id).get().getStart_date().isAfter(LocalDate.now().minusDays(1))) {
            iBookingService.deleteById(id,username);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/checkDate")
    public ResponseEntity<?> checkDate(
            @RequestParam LocalDate start_date,
            @RequestParam LocalDate end_date,
            @RequestParam Long id) {
        List<Bookings> bookings = iBookingService.checkDate(start_date, end_date, id);
        return new ResponseEntity<>(bookings,HttpStatus.OK);
    }

    @GetMapping("/history-booking/{id}")
    public ResponseEntity<List<HistoryBooking>> showHistory(@PathVariable Long id, @RequestHeader ("Authorization") String tokenHeader) {
        String token = tokenHeader.substring(7);
        String username = jwtService.getUsernameFromJwtToken(token);
        List<HistoryBooking> houseList = iBookingService.findAllByAccount(id,username);
        return new ResponseEntity<>(houseList,HttpStatus.OK);
    }

    @PutMapping("/editStatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id) {
        iBookingService.updateStatus(id);
        return new ResponseEntity<>("Booking status updated successfully", HttpStatus.OK);
    }
}
package org.example.rentapplicationbe.controller;


import org.example.rentapplicationbe.config.service.JwtService;
import org.example.rentapplicationbe.model.Entity.Bookings;
import org.example.rentapplicationbe.model.dto.BookingDTO;
import org.example.rentapplicationbe.service.IAccountService;
import org.example.rentapplicationbe.services.bookings.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/booking")
public class BookingController {
    @Autowired
    private IBookingService iBookingService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IAccountService iAccountService;
    @GetMapping("/dto")
    public ResponseEntity<List<BookingDTO>> findAllBookingInfo(@RequestHeader("Authorization") String tokenHeader, @RequestParam Long idHouse, @RequestParam LocalDate start_date, @RequestParam LocalDate end_date) {
        String token = tokenHeader.substring(7);
        String username1 = jwtService.getUsernameFromJwtToken(token);
        iAccountService.findAccountByAccountName(username1);
        List<BookingDTO> bookingDTOS = iBookingService.getAllBookingInfo(idHouse,start_date, end_date);
        return new ResponseEntity<>(bookingDTOS, HttpStatus.OK);
    }
    @PostMapping("create/booking")
    public ResponseEntity<Bookings> createBooking(@RequestHeader("Authorization") String tokenHeader,
                                                  @RequestParam("idHouse") Long idHouse,
                                                  @RequestParam("start_date")  LocalDate startDate,
                                                  @RequestParam("end_date")  LocalDate endDate,
                                                  @RequestParam("accountId") Long accountId) {
        String token = tokenHeader.substring(7);
        String username1 = jwtService.getUsernameFromJwtToken(token);
        iAccountService.findAccountByAccountName(username1);
        Bookings booking = iBookingService.saveBooking(idHouse, startDate, endDate, accountId);

        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

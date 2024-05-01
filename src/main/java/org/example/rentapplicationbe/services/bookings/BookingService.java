package org.example.rentapplicationbe.services.bookings;

import org.example.rentapplicationbe.model.Entity.Bookings;
import org.example.rentapplicationbe.model.dto.BookingDTO;
import org.example.rentapplicationbe.repository.IBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService implements IBookingService{
    @Autowired
    private IBookingRepository iBookingRepository;

    @Override
    public List<BookingDTO> getAllBookingInfo(Long idHouse, LocalDate start_date, LocalDate end_date) {
        return iBookingRepository.getAllBookingInfo(idHouse,start_date,end_date);
    }

    @Override
    public Bookings saveBooking(Long idHouse, LocalDate start_date, LocalDate end_date, Long accountId) {
        return iBookingRepository.saveBooking(idHouse,start_date,end_date,accountId);
    }
}

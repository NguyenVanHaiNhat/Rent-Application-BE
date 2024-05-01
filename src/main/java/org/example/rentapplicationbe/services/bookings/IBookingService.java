package org.example.rentapplicationbe.services.bookings;

import org.example.rentapplicationbe.model.Entity.Bookings;
import org.example.rentapplicationbe.model.dto.BookingDTO;

import java.time.LocalDate;
import java.util.List;

public interface IBookingService {
    List<BookingDTO> getAllBookingInfo(Long idHouse, LocalDate start_date, LocalDate end_date);

    Bookings saveBooking(Long idHouse, LocalDate start_date, LocalDate end_date, Long accountId);
}

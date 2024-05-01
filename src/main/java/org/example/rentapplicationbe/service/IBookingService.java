package org.example.rentapplicationbe.service;

import org.example.rentapplicationbe.model.Entity.Bookings;
import org.example.rentapplicationbe.model.dto.BookHouseDTO;

import java.time.LocalDate;
import java.util.List;

public interface IBookingService {
    void save(BookHouseDTO bookHouseDTO);
    void deleteById(Long id);

}

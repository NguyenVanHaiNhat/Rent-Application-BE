package org.example.rentapplicationbe.service;

import org.example.rentapplicationbe.model.Entity.Bookings;
import org.example.rentapplicationbe.model.dto.BookHouseDTO;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IBookingService {
    void save(BookHouseDTO bookHouseDTO);
    void deleteById(Long id,String username);
    List<Bookings> checkDate(LocalDate start_date,LocalDate end_date,Long id );
}

package org.example.rentapplicationbe.service;

import org.example.rentapplicationbe.model.Entity.Bookings;
import org.example.rentapplicationbe.model.dto.BookHouseDTO;
import org.example.rentapplicationbe.repository.IBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService implements IBookingService {
    @Autowired
    private IBookingRepository iBookingRepository;
    @Override
    public void save(BookHouseDTO bookHouseDTO) {
        iBookingRepository.saveBookAHouse(bookHouseDTO);
    }

    @Override
    public void deleteById(Long id) {
        iBookingRepository.deleteById(id);
    }
}

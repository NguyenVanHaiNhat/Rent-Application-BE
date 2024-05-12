package org.example.rentapplicationbe.service;

import jakarta.transaction.Transactional;
import org.example.rentapplicationbe.model.Entity.Bookings;
import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.model.dto.BookHouseDTO;
import org.example.rentapplicationbe.model.dto.HistoryBooking;
import org.example.rentapplicationbe.model.dto.TotalIncome;
import org.example.rentapplicationbe.model.dto.TotalIncomeRange;
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
        iBookingRepository.updateHouseStatusToRented(bookHouseDTO);
    }

    @Override
    public void deleteById(Long id,String username) {
        iBookingRepository.deleteById(id);
    }

    @Override
    public List<Bookings> checkDate(LocalDate start_date, LocalDate end_date, Long id) {
        return iBookingRepository.checkDate(start_date,end_date,id);
    }

    @Override
    public List<HistoryBooking> findAllByAccount(Long id, String username) {
        return iBookingRepository.findAllByAccount(id);
    }

    @Transactional
    @Override
    public void updateStatus(Long id) {
        iBookingRepository.updateStatus(id);
    }

    @Override
    public int checkIdAccountAndStatus(Long id_house,Long id_account) {
        return iBookingRepository.checkIdAccountAndStatus(id_house,id_account);
    }
    @Override
    public List<TotalIncome> getTotalIncome(Long id, String username) {
        return iBookingRepository.getTotalIncome(id);
    }

    @Override
    public List<TotalIncomeRange> getTotalIncomeRange(Long id, String startMonthYear, String endMonthYear, String username) {
        return iBookingRepository.getTotalIncomeRange(id,startMonthYear,endMonthYear);
    }


}

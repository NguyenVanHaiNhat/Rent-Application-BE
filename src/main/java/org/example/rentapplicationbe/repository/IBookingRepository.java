package org.example.rentapplicationbe.repository;

import org.example.rentapplicationbe.model.Entity.Bookings;
import org.example.rentapplicationbe.model.dto.BookingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IBookingRepository extends JpaRepository<Bookings,Long> {

    @Query(nativeQuery = true, value = "SELECT " +
            "    b.id AS id, " +
            "    b.id_house AS idHouse, " +
            "    b.start_date AS start_date, " +
            "    b.end_date AS end_date, " +
            "    DATEDIFF(b.end_date, b.start_date) AS totalDate, " +
            "    DATEDIFF(b.end_date, b.start_date) * h.price_of_day AS totalMoney " +
            "FROM " +
            "    bookings b " +
            "INNER JOIN " +
            "    house h ON b.id_house = h.id " +
            "WHERE " +
            "    b.start_date >= :start_date AND b.end_date <= :end_date AND " +
            "    h.id = :idHouse")
    List<BookingDTO> getAllBookingInfo(@Param("idHouse") Long idHouse, @Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date);
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO bookings (id_house, start_date, end_date, total_order, status, id_account) VALUES (:idHouse, :start_date, :end_date, 1, 'Đang chờ phòng', :accountId)")
    Bookings saveBooking(@Param("idHouse") Long idHouse, @Param("start_date") LocalDate start_date, @Param("end_date") LocalDate end_date, @Param("accountId") Long accountId);
}

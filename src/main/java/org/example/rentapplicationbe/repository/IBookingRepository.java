package org.example.rentapplicationbe.repository;

import jakarta.transaction.Transactional;
import org.example.rentapplicationbe.model.Entity.Bookings;
import org.example.rentapplicationbe.model.dto.BookHouseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Bookings,Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bookings(start_date, end_date, total_order, id_house, id_account, status) " +
            "SELECT :#{#bookings.start_date}, :#{#bookings.end_date}, " +
            "DATEDIFF(:#{#bookings.end_date}, :#{#bookings.start_date}) * " +
            "(SELECT price_of_day FROM house WHERE id = :#{#bookings.idHouse}), " +
            ":#{#bookings.idHouse}, :#{#bookings.idAccount}, 'RENTED' " +
            "FROM dual " +
            "WHERE NOT EXISTS (SELECT * FROM bookings " +
            "WHERE id_house = :#{#bookings.idHouse} " +
            "AND ((start_date <= :#{#bookings.start_date} AND end_date >= :#{#bookings.start_date}) " +
            "OR (start_date <= :#{#bookings.end_date} AND end_date >= :#{#bookings.end_date}) " +
            "OR (start_date >= :#{#bookings.start_date} AND end_date <= :#{#bookings.end_date}))) " +
            "AND :#{#bookings.start_date} >= CURRENT_DATE", nativeQuery = true)
    void saveBookAHouse(@Param("bookings") BookHouseDTO bookHouseDTO);

}

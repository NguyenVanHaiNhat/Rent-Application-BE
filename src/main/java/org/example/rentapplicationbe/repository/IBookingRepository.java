package org.example.rentapplicationbe.repository;

import jakarta.transaction.Transactional;
import org.example.rentapplicationbe.model.Entity.Bookings;
import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.model.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IBookingRepository extends JpaRepository<Bookings,Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bookings(start_date, end_date, total_order, id_house, id_account, status) " +
            "SELECT :#{#bookings.start_date}, :#{#bookings.end_date}, " +
            "(DATEDIFF(:#{#bookings.end_date}, :#{#bookings.start_date}) + 1) * " +
            "(SELECT price_of_day FROM house WHERE id = :#{#bookings.idHouse}), " +
            ":#{#bookings.idHouse}, :#{#bookings.idAccount}, " +
            "IF(:#{#bookings.start_date} = CURRENT_DATE, 'Đang ở', 'Đang chờ nhận phòng') " +
            "FROM dual " +
            "WHERE NOT EXISTS (SELECT * FROM bookings " +
            "WHERE id_house = :#{#bookings.idHouse} " +
            "AND bookings.status = 'Đang chờ nhận phòng' " +
            "AND ((start_date <= :#{#bookings.end_date} AND end_date >= :#{#bookings.start_date}) " +
            "OR (start_date = :#{#bookings.start_date} AND end_date = :#{#bookings.end_date}) " +
            "OR (start_date >= :#{#bookings.start_date} AND start_date <= :#{#bookings.end_date}) " +
            "OR (end_date >= :#{#bookings.start_date} AND end_date <= :#{#bookings.end_date}))) " +
            "AND :#{#bookings.start_date} >= CURRENT_DATE", nativeQuery = true)
    void saveBookAHouse(@Param("bookings") BookHouseDTO bookHouseDTO);

    @Modifying
    @Transactional
    @Query(value = "UPDATE house SET status = 'Đang cho thuê' WHERE id = :#{#bookings.idHouse}", nativeQuery = true)
    void updateHouseStatusToRented(@Param("bookings") BookHouseDTO bookHouseDTO);


    @Query(nativeQuery = true, value = "SELECT * FROM bookings\n" +
            "WHERE (\n" +
            "    (bookings.start_date BETWEEN :start_date AND :end_date)\n" +
            "    OR (bookings.end_date BETWEEN :start_date AND :end_date)\n" +
            "    OR (:start_date BETWEEN bookings.start_date AND bookings.end_date)\n" +
            "    OR (:end_date BETWEEN bookings.start_date AND bookings.end_date)\n" +
            ")\n" +
            "AND bookings.id_house = :id\n" +
            "AND bookings.status <> 'Đã hủy'\n")
    List<Bookings> checkDate(@Param("start_date") LocalDate start_date,
                             @Param("end_date") LocalDate end_date,
                             @Param("id") Long id);

    @Query(nativeQuery = true,value = "SELECT b.id, b.start_date,b.end_date,h.name_house,b.total_order,h.address,b.status \n" +
            "FROM bookings b\n" +
            "LEFT JOIN house h \n" +
            "ON b.id_house = h.id\n" +
            "WHERE b.id_account = :id")
    List<HistoryBooking> findAllByAccount(@Param("id") Long id);

    @Query(nativeQuery = true, value = "UPDATE bookings SET bookings.status = 'Đã hủy' WHERE bookings.status = 'Đang chờ nhận phòng' AND bookings.id = :id")
    @Modifying
    @Transactional
    void updateStatus(@Param("id") Long id);

    @Query(nativeQuery = true,value = "SELECT COUNT(*) \n" +
            "FROM bookings \n" +
            "WHERE id_house = :id_house AND id_account = :id_account AND status = 'Đã trả phòng'")
int checkIdAccountAndStatus(@Param("id_house")Long id_house,@Param("id_account")Long id_account);

    @Query(nativeQuery = true, value =
            "SELECT " +
                    "    MONTH(b.start_date) AS months, " +
                    "    YEAR(b.start_date) AS years, " +
                    "    COALESCE(SUM(b.total_order), 0) AS total_money " +
                    "FROM " +
                    "    bookings b " +
                    "JOIN " +
                    "    account a ON b.id_account = a.id " +
                    "JOIN " +
                    "    house h ON h.id = b.id_house " +
                    "WHERE " +
                    "    h.id_account = :id " +
                    "    AND b.status <> 'Đã hủy' " +
                    "GROUP BY " +
                    "    YEAR(b.start_date), MONTH(b.start_date) " +
                    "ORDER BY " +
                    "    YEAR(b.start_date), MONTH(b.start_date);")
    List<TotalIncome> getTotalIncome(@Param("id") Long id);


    @Query(nativeQuery = true, value =
            "SELECT " +
                    "    MONTH(b.start_date) AS months, " +
                    "    YEAR(b.start_date) AS years, " +
                    "    COALESCE(SUM(b.total_order), 0) AS total_money " +
                    "FROM " +
                    "    bookings b " +
                    "JOIN " +
                    "    house h ON h.id = b.id_house " +
                    "WHERE " +
                    "    h.id_account = :id " +
                    "    AND b.status <> 'Đã hủy' " +
                    "    AND (YEAR(b.start_date) * 100 + MONTH(b.start_date)) BETWEEN :startMonthYear AND :endMonthYear " +
                    "GROUP BY " +
                    "    YEAR(b.start_date), MONTH(b.start_date) " +
                    "ORDER BY " +
                    "    YEAR(b.start_date), MONTH(b.start_date);")
    List<TotalIncomeRange> getTotalIncomeRange(
            @Param("id") Long id,
            @Param("startMonthYear") String startMonthYear,
            @Param("endMonthYear") String endMonthYear
    );
}

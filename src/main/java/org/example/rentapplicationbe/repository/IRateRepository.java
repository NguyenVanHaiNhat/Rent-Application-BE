package org.example.rentapplicationbe.repository;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.example.rentapplicationbe.model.Entity.Rate;
import org.example.rentapplicationbe.model.dto.RateDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRateRepository extends JpaRepository<Rate, Long> {
    @Query(nativeQuery = true, value = "select acc.avatar, acc.username, r.time_rate, r.stars, r.content\n" +
            "from account acc\n" +
            "join rate r on r.id_account = acc.id\n" +
            "join bookings b on b.id_account = acc.id\n" +
            "where b.id_house = :id")
    List<RateDTO> findAllRateByIdHouse(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO rate (id_account, id_house,time_rate, stars, content) " +
            "SELECT b.id_account, b.id_house,current_time(),:#{#rate.stars},:#{#rate.content} " +
            "FROM bookings b " +
            "WHERE  b.id_house = :id AND b.id_account = :id_account AND b.status = 'Đã trả phòng'")
    void createRate(@Param("id") Long id,@Param("id_account") Long id_account, @Param("rate") Rate rate);

}

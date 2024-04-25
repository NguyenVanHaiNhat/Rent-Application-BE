package org.example.rentapplicationbe.repository;


import org.example.rentapplicationbe.model.Entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface IHouseRepository extends JpaRepository<House, Long> {
    @Query(nativeQuery = true, value = "SELECT DISTINCT h.* " +
            "FROM house h " +
            "INNER JOIN bookings b ON h.id = b.id_house " +
            "INNER JOIN account acc ON b.id_account = acc.id " +
            "WHERE acc.id = :id ")
    List<House> findByIdDetailHouse(@Param("id") Long id);
}











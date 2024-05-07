package org.example.rentapplicationbe.repository;


import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.model.dto.HouseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IHouseRepository extends JpaRepository<House, Long> {
    @Query(nativeQuery = true, value = "SELECT DISTINCT h.* \n" +
            "FROM house h \n" +
            "WHERE id_account = :id and name_house like :name and status like :status ")
    List<House> findByIdDetailHouse(@Param("id") Long id, @Param("name") String name, @Param("status") String status);

    @Query(nativeQuery = true, value = "SELECT DISTINCT h.* " +
            "FROM house h " +
            "LEFT JOIN bookings b ON h.id = b.id_house " +
            "WHERE h.num_of_bedrooms LIKE :bedrooms " +
            "AND h.num_of_bathrooms LIKE :bathrooms " +
            "AND h.address LIKE :address " +
            "AND h.price_of_day LIKE :price " +
            "AND (b.end_date <= :checkInDate OR b.start_date >= :checkOutDate " +
            "OR b.start_date IS NULL OR b.end_date IS NULL)")
    List<House> searchAll(@Param("bedrooms") Integer bedrooms,
                          @Param("bathrooms") Integer bathrooms,
                          @Param("address") String address,
                          @Param("price") Long price,
                          @Param("checkInDate") LocalDate checkInDate,
                          @Param("checkOutDate") LocalDate checkOutDate);

    @Query(nativeQuery = true, value = "SELECT house.*, GROUP_CONCAT(image.image_url) AS all_images\n" +
            "FROM house\n" +
            "LEFT JOIN image ON house.id = image.id_house\n" +
            "GROUP BY house.id\n" +
            "having house.id = :id ")
    Optional<HouseDetail> findByIdHouseImages(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT DISTINCT h.* " +
            "FROM house h " +
            "WHERE h.id_account = :id AND h.status = 'đang cho thuê'")
    List<House> findRentedHousesByOwnerId(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT DISTINCT h.* " +
            "FROM house h " +
            "WHERE h.id_account = :id AND h.status = 'bảo trì'")
    List<House> findMaintenanceHousesByOwnerId(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT DISTINCT h.* " +
            "FROM house h " +
            "WHERE h.id_account = :id AND h.status = 'đang trống'")
    List<House> findAvailableHousesByOwnerId(@Param("id") Long id);


}











package org.example.rentapplicationbe.repository;


import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.model.dto.HouseDetail;
import org.hibernate.query.NativeQuery;
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

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT h.* " +
                    "FROM house h " +
                    "LEFT JOIN bookings b ON h.id = b.id_house " +
                    "WHERE (:bedrooms IS NULL OR h.num_of_bedrooms <= :bedrooms) " +
                    "AND (:bathrooms IS NULL OR h.num_of_bathrooms <= :bathrooms) " +
                    "AND (h.address LIKE CONCAT('%', :address, '%') OR :address IS NULL) " +
                    "AND (:price IS NULL OR h.price_of_day <= :price) " +
                    "AND h.status <> 'bảo trì' " +
                    "AND NOT EXISTS (" +
                    "   SELECT 1 FROM bookings b2 " +
                    "   WHERE h.id = b2.id_house " +
                    "   AND (:checkInDate BETWEEN b2.start_date AND b2.end_date " +
                    "       OR :checkOutDate BETWEEN b2.start_date AND b2.end_date) " +
                    ")")
    List<House> searchAll(
            @Param("bedrooms") Integer bedrooms,
            @Param("bathrooms") Integer bathrooms,
            @Param("address") String address,
            @Param("price") Long price,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );


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

    @Query(nativeQuery = true, value =
            "SELECT h.*, COUNT(b.id) AS total_bookings " +
                    "FROM house h " +
                    "LEFT JOIN bookings b ON h.id = b.id_house " +
                    "WHERE b.status = 'đã xong' " +
                    "GROUP BY h.id " +
                    "ORDER BY total_bookings DESC " +
                    "LIMIT 5")
    List<House> findTop5MostBookedHouses();
    @Query(nativeQuery = true, value = "UPDATE house SET status = :newStatus WHERE id = :id")
    void updateStatusForHouse(@Param("id") Long id, String newStatus);
}











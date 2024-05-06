package org.example.rentapplicationbe.repository;


import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.model.dto.HouseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface IHouseRepository extends JpaRepository<House, Long> {
        @Query(nativeQuery = true, value = "SELECT DISTINCT h.* \n" +
            "FROM house h \n" +
            "WHERE id_account = :id and name_house like :name and status like :status ")
    List<House> findByIdDetailHouse(@Param("id") Long id, @Param("name") String name, @Param("status") String status);
    @Query(nativeQuery = true,value = "SELECT house.*, GROUP_CONCAT(image.image_url) AS all_images\n" +
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











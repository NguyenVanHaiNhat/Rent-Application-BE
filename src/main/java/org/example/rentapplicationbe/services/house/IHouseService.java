package org.example.rentapplicationbe.services.house;
import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.model.dto.HouseDetail;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IHouseService {
    List<House> findByIdDetailHouse(Long id, String name, String status);
    List<House> searchAll(@Param("bedrooms") Integer bedrooms,
                          @Param("bathrooms") Integer bathrooms,
                          @Param("address") String address,
                          @Param("price") Long price,
                          @Param("checkInDate") LocalDate checkInDate,
                          @Param("checkOutDate") LocalDate checkOutDate);
    List<House> findRentedHousesByOwnerId(Long id);
    List<House> findMaintenanceHousesByOwnerId(Long id);
    List<House> findAvailableHousesByOwnerId(Long id);

    Optional<House> findById(Long id);
    Optional<HouseDetail>findByIdHouseImage(Long id);


    House save(House house);
}

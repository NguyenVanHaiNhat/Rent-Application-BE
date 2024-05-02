package org.example.rentapplicationbe.services.house;
import org.example.rentapplicationbe.model.Entity.House;

import java.util.List;
import java.util.Optional;

public interface IHouseService {
    List<House> findByIdDetailHouse(Long id);
    List<House> findRentedHousesByOwnerId(Long id);
    List<House> findMaintenanceHousesByOwnerId(Long id);
    List<House> findAvailableHousesByOwnerId(Long id);



    Optional<House> findById(Long id);

    House save(House house);
}

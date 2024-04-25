package org.example.rentapplicationbe.services.house;
import org.example.rentapplicationbe.model.Entity.House;

import java.util.List;

public interface IHouseService {
    List<House> findByIdDetailHouse(Long id);
}

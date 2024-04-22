package org.example.rentapplicationbe.services.house;

import org.example.rentapplicationbe.model.Entity.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService implements IHouseService {
    @Autowired
    private IHouseService iHouseService;

    @Override
    public List<House> findByIdDetailHouse(Long id) {
        return iHouseService.findByIdDetailHouse(id);
    }
}

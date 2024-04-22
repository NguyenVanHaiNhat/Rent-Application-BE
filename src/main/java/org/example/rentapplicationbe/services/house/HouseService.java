package org.example.rentapplicationbe.services.house;

import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.repository.IHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService implements IHouseService {
    @Autowired
    private IHouseRepository iHouseRepository;

    @Override
    public List<House> findByIdDetailHouse(Long id) {
        return iHouseRepository.findByIdDetailHouse(id);
    }
}

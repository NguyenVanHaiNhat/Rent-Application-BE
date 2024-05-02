package org.example.rentapplicationbe.services.house;

import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.repository.IHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseService implements IHouseService {
    @Autowired
    private IHouseRepository iHouseRepository;

    @Override
    public List<House> findByIdDetailHouse(Long id) {
        return iHouseRepository.findByIdDetailHouse(id);
    }
    @Override
    public Optional<House> findById(Long id) {
        return iHouseRepository.findById(id);
    }

    @Override
    public House save(House house) {
        return iHouseRepository.save(house);
    }

    @Override
    public void saveHouse(House house) {
        iHouseRepository.save(house);
    }

    @Override
    public List<House> getAllList() {
        return iHouseRepository.getAllList();
    }
}

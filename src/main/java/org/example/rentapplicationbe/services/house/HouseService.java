package org.example.rentapplicationbe.services.house;

import org.example.rentapplicationbe.model.Entity.House;
import org.example.rentapplicationbe.model.dto.HouseDetail;
import org.example.rentapplicationbe.repository.IHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HouseService implements IHouseService {
    @Autowired
    private IHouseRepository iHouseRepository;

    @Override
    public List<House> findByIdDetailHouse(Long id, String name, String status) {
        return iHouseRepository.findByIdDetailHouse(id, "%" + name + "%", "%" + status + "%");
    }

    @Override
    public List<House> searchAll(Integer bedrooms, Integer bathrooms, String address, Long price, LocalDate checkInDate, LocalDate checkOutDate) {
        return iHouseRepository.searchAll(bedrooms,bathrooms,"%" + address + "%", price ,checkInDate,checkOutDate);
    }


    public List<House> findRentedHousesByOwnerId(Long id) {
        return iHouseRepository.findRentedHousesByOwnerId(id);
    }
    public List<House> findMaintenanceHousesByOwnerId(Long id) {
        return iHouseRepository.findMaintenanceHousesByOwnerId(id);
    }
    public List<House> findAvailableHousesByOwnerId(Long id) {
        return iHouseRepository.findAvailableHousesByOwnerId(id);
    }


    @Override
    public Optional<House> findById(Long id) {
        return iHouseRepository.findById(id);
    }
    @Override
    public Optional<HouseDetail> findByIdHouseImage(Long id) {
        return iHouseRepository.findByIdHouseImages(id);
    }

    @Override
    public House save(House house) {
        return iHouseRepository.save(house);
    }
}

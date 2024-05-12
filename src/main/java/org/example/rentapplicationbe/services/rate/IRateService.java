package org.example.rentapplicationbe.services.rate;

import org.example.rentapplicationbe.model.Entity.Rate;
import org.example.rentapplicationbe.model.dto.RateDTO;

import java.util.List;

public interface IRateService {
    List<RateDTO> findAllRateByIdHouse(Long id);

    void createRate(Long id,Long id_account, Rate rate,String username);

}
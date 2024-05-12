package org.example.rentapplicationbe.services.rate;

import org.example.rentapplicationbe.model.Entity.Rate;
import org.example.rentapplicationbe.model.dto.RateDTO;
import org.example.rentapplicationbe.repository.IRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateService implements IRateService {
    @Autowired
    private IRateRepository iRateRepository;


    @Override
    public List<RateDTO> findAllRateByIdHouse(Long id) {
        return iRateRepository.findAllRateByIdHouse(id);
    }

    @Override
    public void createRate(Long id,Long id_account,Rate rate,String username) {
        iRateRepository.createRate(id,id_account,rate);
    }
}

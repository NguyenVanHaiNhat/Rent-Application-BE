package org.example.rentapplicationbe.services;

import org.example.rentapplicationbe.model.dto.HostDtoDetail;
import org.example.rentapplicationbe.repository.IHostDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostService implements IHostService {
    @Autowired
    private IHostDtoRepository iHostDtoRepository;

    @Override
    public List<HostDtoDetail> getHostInfor(String username) {
        return iHostDtoRepository.getHostInfor();
    }

    @Override
    public Optional<HostDtoDetail> findByIdHost(Long id) {
        return iHostDtoRepository.findByIdHost(id);
    }

    @Override
    public void updateAccountStatus(Long id, String newStatus) {
        iHostDtoRepository.updateAccountStatus(id, newStatus);
    }   
}

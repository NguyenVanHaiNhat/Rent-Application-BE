package org.example.rentapplicationbe.services;

import org.example.rentapplicationbe.model.dto.HostDtoDetail;

import java.util.List;
import java.util.Optional;

public interface IHostService {
    List<HostDtoDetail> getHostInfor();
    Optional<HostDtoDetail> findByIdHost(Long id);
    void updateAccountStatus(Long id, String newStatus);
}

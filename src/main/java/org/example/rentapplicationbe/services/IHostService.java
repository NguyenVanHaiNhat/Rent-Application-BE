package org.example.rentapplicationbe.services;

import org.example.rentapplicationbe.model.dto.HostDtoDetail;
import org.example.rentapplicationbe.model.dto.RentalSchedule;

import java.util.List;
import java.util.Optional;

public interface IHostService {
    List<HostDtoDetail> getHostInfor(String username);
    Optional<HostDtoDetail> findByIdHost(Long id);
    void updateAccountStatus(Long id, String newStatus);
    List<RentalSchedule> getSchedule(Long id);
}

package org.example.rentapplicationbe.service;

import org.example.rentapplicationbe.model.Entity.Role;
import org.example.rentapplicationbe.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleService implements IRoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).get();
    }
}

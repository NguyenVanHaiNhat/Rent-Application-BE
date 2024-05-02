package org.example.rentapplicationbe.config.service;

import org.example.rentapplicationbe.config.UserPrinciple;
import org.example.rentapplicationbe.model.Entity.Account;
import org.example.rentapplicationbe.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    public Account findByUsername(String accountName) {
        return accountRepository.findByUsername(accountName).get();
    }

    public UserDetails loadUserByUsername(String username) {
        return UserPrinciple.build(accountRepository.findByUsername(username).get());
    }
}

package org.example.rentapplicationbe.service;

import org.example.rentapplicationbe.config.JwtTokenUtil;
import org.example.rentapplicationbe.model.Entity.Account;
import org.example.rentapplicationbe.model.dto.ChangePasswordUser;
import org.example.rentapplicationbe.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public Optional<Account> findAccountByEmail(String email) {
//        return accountRepository.findByEmail(email);
//    }

    @Override
    public Optional<Account> findAccountByAccountName(String accountName) {
        return accountRepository.findByUsername(accountName);
    }

    @Override
    public Optional<Account> findAccountByPhone(String phoneNumber) {
        return accountRepository.findByPhone(phoneNumber);
    }

    @Override
    public String login(String nameAccount, String passWord) throws Exception {
        Optional<Account> optionalUser = accountRepository.findByUsername(nameAccount);
        if (optionalUser.isEmpty()) {
            throw new DataFormatException("Sai tai khoan hoac mat khau ");
        }
        Account existingUser = optionalUser.get();

        // chua dang nhap google


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                nameAccount, passWord, existingUser.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(optionalUser.get());
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    public void changePassword(String username,ChangePasswordUser request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<Account> user = accountRepository.findByUsername(username);
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.get().getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getCheckNewPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
        accountRepository.save(user.get());
    }

}

package org.example.rentapplicationbe.service;

import org.example.rentapplicationbe.model.Entity.Account;
import org.example.rentapplicationbe.model.dto.AccountUserDTO;
import org.example.rentapplicationbe.model.dto.ChangePasswordUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
//    Optional<Account> findAccountByEmail(String email);


    Optional<Account> findAccountByAccountName(String accountName);
    List<Account> checkUserName(String accountName);


    Optional<Account> findAccountByPhone(String phoneNumber);

    String login(String nameAccount, String passWord) throws Exception;

    void save(Account account);

    void changePassword(String username, ChangePasswordUser changePasswordUser);

    List<AccountUserDTO> findAllUser(String username);

    void updateAccountStatus(Long id, String newStatus);

}

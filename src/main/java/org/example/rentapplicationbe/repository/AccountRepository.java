package org.example.rentapplicationbe.repository;

import org.example.rentapplicationbe.model.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
//    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsername(String accountName);

    Optional<Account> findByPhone(String phoneNumber);

    boolean existsByUsername(String nameAccount);
    @Query(value = "select * from account where account.username = :userName", nativeQuery = true)
    List<Account> checkUserName(String userName);

}
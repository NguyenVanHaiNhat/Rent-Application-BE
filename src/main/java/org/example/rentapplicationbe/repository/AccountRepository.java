    package org.example.rentapplicationbe.repository;

    import org.example.rentapplicationbe.model.Entity.Account;
    import org.example.rentapplicationbe.model.dto.AccountUserDTO;
    import org.example.rentapplicationbe.model.dto.HostDtoDetail;
    import org.example.rentapplicationbe.model.dto.UserDetail;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;
    import org.springframework.data.jpa.repository.Query;

    import java.util.List;
    import java.util.Optional;
    import java.util.List;

    @Repository
    public interface AccountRepository extends JpaRepository<Account, Long> {
    //    Optional<Account> findByEmail(String email);

        Optional<Account> findByUsername(String accountName);

        Optional<Account> findByPhone(String phoneNumber);

        boolean existsByUsername(String nameAccount);
        @Query(value = "select * from account where account.username = :userName", nativeQuery = true)
        List<Account> checkUserName(@Param("userName") String userName);

        @Query(nativeQuery = true, value = "select id, full_name, phone, status\n" +
                "from `account`\n" +
                "where id_role = 1;")
        List<AccountUserDTO> findAllUser();
        Account findAccountByUsername(String username);
@Query(nativeQuery = true, value = "SELECT \n" +
        "       acc.id as id,\n" +
        "\t\tacc.avatar AS avatar,\n" +
        "\t\tacc.username AS username,\n" +
        "\t\tacc.full_name AS fullName,\n" +
        "        acc.phone AS phoneNumber,\n" +
        "\t\tacc.`status` AS status,\n" +
        " \t\tCOALESCE(SUM(DATEDIFF(b.end_date, b.start_date) * h.price_of_day), 0) AS amountSpent\n" +
        " \tFROM account acc\n" +
        " \tLEFT JOIN bookings b ON acc.id = b.id_account\n" +
        " \tLEFT JOIN house h ON b.id_house = h.id\n" +
        " \tGROUP BY acc.id, acc.avatar, acc.username, acc.full_name, acc.phone, acc.`status`\n" +
        "     having acc.id = :id")
        Optional<UserDetail> findByIdUserDetail(@Param("id") Long id);

    }
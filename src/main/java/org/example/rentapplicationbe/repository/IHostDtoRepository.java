package org.example.rentapplicationbe.repository;


import jakarta.transaction.Transactional;
import org.example.rentapplicationbe.model.DTO.HostDtoDetail;
import org.example.rentapplicationbe.model.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
public interface IHostDtoRepository extends JpaRepository<Account, Long> {
    @Query(nativeQuery = true, value = "SELECT \n" +
            "        acc.id as id,\n" +
            "    accInfo.full_name AS fullName,\n" +
            "    acc.phone AS phone,\n" +
            "    COALESCE(SUM(DATEDIFF(b.end_date, b.start_date) * h.price_of_day), 0) AS totalRevenue,\n" +
            "    accInfo.`status` AS status,\n" +
            "    COUNT(h.id) AS numberOfHouses\n" +
            "FROM \n" +
            "    account acc\n" +
            "JOIN \n" +
            "    account_info accInfo ON acc.id = accInfo.id_account\n" +
            "LEFT JOIN \n" +
            "    bookings b ON acc.id = b.id_account\n" +
            "LEFT JOIN \n" +
            "    house h ON b.id_house = h.id\n" +
            "GROUP BY \n" +
            "    acc.id, accInfo.full_name, acc.phone, accInfo.`status`;")
    List<HostDtoDetail> getHostInfor();
    @Query(nativeQuery = true, value = "SELECT \n" +
            "        acc.id as id,\n" +
            "\t\taccInfo.avatar AS avatar,\n" +
            "\t\tacc.username AS username,\n" +
            "\t\taccInfo.full_name AS fullName,\n" +
            "\t\tacc.phone AS phone,\n" +
            "\t\taccInfo.address AS address,\n" +
            "\t\taccInfo.`status` AS status,\n" +
            "\t\tCOALESCE(SUM(DATEDIFF(b.end_date, b.start_date) * h.price_of_day), 0) AS totalRevenue,\n" +
            "        COUNT(h.id) AS numberOfHouses\n" +
            "\tFROM account acc\n" +
            "\tJOIN account_info accInfo ON acc.id = accInfo.id_account\n" +
            "\tLEFT JOIN bookings b ON acc.id = b.id_account\n" +
            "\tLEFT JOIN house h ON b.id_house = h.id\n" +
            "\tGROUP BY acc.id, accInfo.avatar, acc.username, accInfo.full_name, acc.phone, accInfo.address, accInfo.`status`\n" +
            "    having acc.id = :id")
    Optional<HostDtoDetail> findByIdHost(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE account_info SET status = :newStatus WHERE id_account = :id")
    void updateAccountStatus(@Param("id") Long id, @Param("newStatus") String newStatus);
}

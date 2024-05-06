package org.example.rentapplicationbe.repository;


import jakarta.transaction.Transactional;
import org.example.rentapplicationbe.model.dto.HostDtoDetail;
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
            "    acc.full_name AS fullName,\n" +
            "    acc.phone AS phone,\n" +
            "    COALESCE(SUM(DATEDIFF(b.end_date, b.start_date) * h.price_of_day), 0) AS totalRevenue,\n" +
            "    acc.`status` AS status,\n" +
            "    COUNT(h.id) AS numberOfHouses\n" +
            "FROM \n" +
            "    account acc\n" +
            "LEFT JOIN \n" +
            "    bookings b ON acc.id = b.id_account\n" +
            "LEFT JOIN \n" +
            "    house h ON b.id_house = h.id\n" +
            "WHERE acc.id_role = 2 \n" +
            "GROUP BY \n" +
            "    acc.id, acc.full_name, acc.phone, acc.`status`;")
    List<HostDtoDetail> getHostInfor();
    @Query(nativeQuery = true, value = "SELECT \n" +
            "        acc.id as id,\n" +
            "\t\tacc.avatar AS avatar,\n" +
            "\t\tacc.username AS username,\n" +
            "\t\tacc.full_name AS fullName,\n" +
            "\t\tacc.phone AS phone,\n" +
            "\t\tacc.address AS address,\n" +
            "\t\tacc.`status` AS status,\n" +
            "\t\tCOALESCE(SUM(DATEDIFF(b.end_date, b.start_date) * h.price_of_day), 0) AS totalRevenue,\n" +
            "        COUNT(h.id) AS numberOfHouses\n" +
            "\tFROM account acc\n" +
            "\tLEFT JOIN bookings b ON acc.id = b.id_account\n" +
            "\tLEFT JOIN house h ON b.id_house = h.id\n" +
            "\tGROUP BY acc.id, acc.avatar, acc.username, acc.full_name, acc.phone, acc.address, acc.`status`\n" +
            "    having acc.id = :id")
    Optional<HostDtoDetail> findByIdHost(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE account SET status = :newStatus WHERE id = :id")
    void updateAccountStatus(@Param("id") Long id, @Param("newStatus") String newStatus);
}

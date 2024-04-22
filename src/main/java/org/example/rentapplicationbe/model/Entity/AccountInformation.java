package org.example.rentapplicationbe.model.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "account_info")
public class AccountInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String full_name;
    private String address;
    private String status;
    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;
}

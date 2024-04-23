package org.example.rentapplicationbe.model.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "bookings")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_house")
    private House house;
    @ManyToOne
    @JoinColumn(name = "id_account")
    private Account account;
    private LocalDate start_date;
    private LocalDate end_date;
    private Integer total_order;
    private String status;
}

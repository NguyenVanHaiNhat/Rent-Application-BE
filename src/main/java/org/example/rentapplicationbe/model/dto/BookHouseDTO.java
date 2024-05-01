package org.example.rentapplicationbe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookHouseDTO {
    private Long id;
    private LocalDate start_date;
    private LocalDate end_date;
    private Integer total_order;
    private Long idHouse;
    private Long idAccount;
    private BookingStatus status;
}

package org.example.rentapplicationbe.model.dto;

import java.time.LocalDate;

public interface BookingDTO {
    Long getId();
    LocalDate getStart_date();
    LocalDate getEnd_date();
    Integer getTotalDate();
    Double getTotalMoney();

}

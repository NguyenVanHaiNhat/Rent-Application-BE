package org.example.rentapplicationbe.model.dto;

import java.time.LocalDate;

public interface RentalSchedule {
    LocalDate getStart_date();
    LocalDate getEnd_date();
    String getName_house();
    String getFull_name();
    String getStatus();
}

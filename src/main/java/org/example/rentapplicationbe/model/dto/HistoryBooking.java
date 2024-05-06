package org.example.rentapplicationbe.model.dto;

import java.time.LocalDate;

public interface HistoryBooking {
    Long getId();
    LocalDate getStart_date();
    LocalDate getEnd_date();
    Long getName_house();
    int getTotal_order();
    String getAddress();
    String getStatus();
}

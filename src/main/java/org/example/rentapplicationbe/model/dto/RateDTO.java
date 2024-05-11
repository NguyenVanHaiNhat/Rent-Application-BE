package org.example.rentapplicationbe.model.dto;


import java.time.LocalDateTime;

public interface RateDTO {
    Long getId();

    String getAvatar();

    String getUsername();

    LocalDateTime getTime_rate();

    Integer getStars();

    String getContent();
}


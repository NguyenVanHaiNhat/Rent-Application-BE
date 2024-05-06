package org.example.rentapplicationbe.model.dto;

public interface UserDetail {
    Long getId();
    String getAvatar();
    String getUserName();
    String getFullName();
    String getPhoneNumber();
    String getStatus();
    Double getAmountSpent();
    String getHistoryRent();
}

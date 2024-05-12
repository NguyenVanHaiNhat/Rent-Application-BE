package org.example.rentapplicationbe.model.dto;

public interface HouseDetail {
    Long getId();

    String getName_house();

    String getAddress();

    Integer getNum_of_bedrooms();

    Integer getNum_of_bathrooms();

    String getDescription();

    Long getPrice_of_day();

    String getImage();

    String getAll_images();
    Long getId_account();
}

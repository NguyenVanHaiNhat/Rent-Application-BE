package org.example.rentapplicationbe.model.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class HouseDTO {
    private Long id;
    @NotNull
    private String name_house;
    @NotNull
    private String address;
    @NotNull
    @Min(1)
    @Max(10)
    @Digits(integer = 2, fraction = 0, message = "Số giường ngủ phải lớn hơn 1 và nhỏ hơn 10")
    private Integer num_of_bedrooms;
    @NotNull
    @Min(1)
    @Max(3)
    @Digits(integer = 1, fraction = 0, message = "Số nhà vệ sinh phải lớn hơn 1 và nhỏ hơn 3")
    private Integer num_of_bathrooms;
    @NotNull
    @Min(1000)
    @Max(999999999)
    @Digits(integer = 12, fraction = 0, message = "Phí quản lý phải lớn hơn 1 và < 9,999,999,999,999")
    private Long price_of_day;
    @NotNull
    private String image;

    public HouseDTO(Long id, String name_house, String address, Integer num_of_bedrooms, Integer num_of_bathrooms, Long price_of_day, String image) {
        this.id = id;
        this.name_house = name_house;
        this.address = address;
        this.num_of_bedrooms = num_of_bedrooms;
        this.num_of_bathrooms = num_of_bathrooms;
        this.price_of_day = price_of_day;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_house() {
        return name_house;
    }

    public void setName_house(String name_house) {
        this.name_house = name_house;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNum_of_bedrooms() {
        return num_of_bedrooms;
    }

    public void setNum_of_bedrooms(Integer num_of_bedrooms) {
        this.num_of_bedrooms = num_of_bedrooms;
    }

    public Integer getNum_of_bathrooms() {
        return num_of_bathrooms;
    }

    public void setNum_of_bathrooms(Integer num_of_bathrooms) {
        this.num_of_bathrooms = num_of_bathrooms;
    }

    public Long getPrice_of_day() {
        return price_of_day;
    }

    public void setPrice_of_day(Long price_of_day) {
        this.price_of_day = price_of_day;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

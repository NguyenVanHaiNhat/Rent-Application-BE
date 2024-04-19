package org.example.rentapplicationbe.model.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name_house;
    private String address;
    private Integer num_of_bedrooms;
    private Integer num_of_bathrooms;
    private Long price_of_day;
    private String image;
}
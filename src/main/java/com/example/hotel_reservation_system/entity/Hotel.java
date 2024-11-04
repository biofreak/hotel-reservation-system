package com.example.hotel_reservation_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "hotel")
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String caption;
    private String city;
    private String address;
    private Float distance;
    private Float rating;
    @Column(name = "number_of_rating")
    private Integer numberOfRating;
}

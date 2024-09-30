package com.example.hotel_reservation_system.dto;

import lombok.*;

@Data
@AllArgsConstructor     
@NoArgsConstructor
@Builder
public class HotelResponse {
    private Long id;
    private String title;
    private String caption;
    private String city;
    private String address;
    private Float distance;
    private Integer rating;
    private Integer scores;
}

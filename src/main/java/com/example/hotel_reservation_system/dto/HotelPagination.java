package com.example.hotel_reservation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelPagination {
    private String title;
    private String city;
    private Integer rating;
    private Integer numberOfRating;
}

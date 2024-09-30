package com.example.hotel_reservation_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponse {
    private Long id;
    private String name;
    private String description;
    private Integer number;
    private Double price;
    private Integer capacity;
    Map<Instant, Instant> occupation;
    private HotelResponse hotel;
}

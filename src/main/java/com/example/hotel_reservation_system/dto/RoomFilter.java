package com.example.hotel_reservation_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class RoomFilter {
    private Long id;
    private String title;
    @JsonProperty("min_price")
    private Double minPrice;
    @JsonProperty("max_price")
    private Double maxPrice;
    @JsonProperty("number_of_guests")
    private Integer numberOfGuests;
    @JsonProperty("check_in")
    private Instant checkIn;
    @JsonProperty("check_out")
    private Instant checkIOut;
    @JsonProperty("hotel_id")
    private Long hotelId;
}

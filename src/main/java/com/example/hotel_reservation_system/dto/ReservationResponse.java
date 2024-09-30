package com.example.hotel_reservation_system.dto;

import com.example.hotel_reservation_system.entity.Room;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponse {
    private Long id;
    @JsonProperty("check_id")
    private Instant checkIn;
    @JsonProperty("check_out")
    private Instant checkOut;
    private RoomResponse room;
    private UserResponse visitor;
}

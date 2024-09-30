package com.example.hotel_reservation_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Future;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class ReservationRequest {
    @FutureOrPresent(message = "Начало брониования должно следовать за текущим временем.")
    @JsonProperty("check_in")
    private Instant checkIn;
    @Future(message = "Начало брониования должно следовать за текущим моментом.")
    @JsonProperty("check_out")
    private Instant checkOut;
    @JsonProperty("room_id")
    private Long roomId;
    @JsonProperty("visitor_id")
    private Long visitorId;

    public Boolean validate() {
        return checkIn.isBefore(checkOut);
    }
}

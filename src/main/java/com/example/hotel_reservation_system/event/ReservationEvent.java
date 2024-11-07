package com.example.hotel_reservation_system.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEvent implements Serializable {
    @JsonProperty("user_id")
    private Long userId;
    @JsonSerialize(using = InstantSerializer.class)
    @JsonProperty("check_in")
    private Instant checkIn;
    @JsonSerialize(using = InstantSerializer.class)
    @JsonProperty("check_out")
    private Instant checkOut;
}

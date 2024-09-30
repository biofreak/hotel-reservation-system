package com.example.hotel_reservation_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RoomRequest {
    private String name;
    private String description;
    @NotNull(message = "Для комнаты поле number должно быть заполнено.")
    private Integer number;
    private Double price;
    private Integer capacity;
    @NotNull(message = "Для комнаты поле hotel_id должно быть заполнено.")
    @JsonProperty("hotel_id")
    private Long hotelId;
}

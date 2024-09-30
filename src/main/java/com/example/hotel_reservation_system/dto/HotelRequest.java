package com.example.hotel_reservation_system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class HotelRequest {
    @NotNull(message = "Для отеля поле title должно быть заполнено")
    private String title;
    private String caption;
    @NotNull(message = "Для отеля поле city должно быть заполнено")
    private String city;
    private String address;
    private Float distance;
}

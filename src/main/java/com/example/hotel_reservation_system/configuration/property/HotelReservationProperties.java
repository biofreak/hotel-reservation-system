package com.example.hotel_reservation_system.configuration.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record HotelReservationProperties(int paginationLimit) {
}

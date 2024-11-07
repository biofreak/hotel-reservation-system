package com.example.hotel_reservation_system.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;

@Data
@RequiredArgsConstructor
@Document(collection = "reservation")
public class Statistics implements Serializable {
    @Id
    private String id;
    @Field(name = "check_in")
    private Instant checkIn;
    @Field(name = "check_out")
    private Instant checkOut;
    @Field(name = "user_id")
    private Long userId;
}

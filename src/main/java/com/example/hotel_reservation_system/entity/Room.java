package com.example.hotel_reservation_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "room")
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer number;
    private Double price;
    private Integer capacity;
    @ElementCollection(fetch = FetchType.EAGER, targetClass = Instant.class)
    @CollectionTable(name = "room_occupation", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"))
    @MapKeyColumn(name = "start_date")
    @Column(name = "end_date")
    private Map<Instant, Instant> occupation = new HashMap<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "hotel_id", referencedColumnName = "id")
    private com.example.hotel_reservation_system.entity.Hotel hotel;
}

package com.example.hotel_reservation_system.repository;

import com.example.hotel_reservation_system.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    Optional<Room> findByHotel_idAndNumber(Long hotelId, Integer number);
}

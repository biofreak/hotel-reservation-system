package com.example.hotel_reservation_system.repository;

import com.example.hotel_reservation_system.entity.Room;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends PagingAndSortingRepository<Room, Long>, CrudRepository<Room, Long>, JpaSpecificationExecutor<Room> {
    Optional<Room> findByHotel_idAndNumber(Long hotelId, Integer number);
}

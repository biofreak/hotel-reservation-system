package com.example.hotel_reservation_system.repository;

import com.example.hotel_reservation_system.entity.Reservation;
import com.example.hotel_reservation_system.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
}

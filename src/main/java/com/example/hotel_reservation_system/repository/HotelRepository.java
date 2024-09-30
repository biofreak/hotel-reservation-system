package com.example.hotel_reservation_system.repository;

import com.example.hotel_reservation_system.entity.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long>, CrudRepository<Hotel, Long> {
    Optional<Hotel> findByTitleAndCity(String title, String city);
}

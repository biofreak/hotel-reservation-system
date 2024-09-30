package com.example.hotel_reservation_system.controller;

import com.example.hotel_reservation_system.configuration.property.HotelReservationProperties;
import com.example.hotel_reservation_system.dto.HotelPagination;
import com.example.hotel_reservation_system.dto.HotelRequest;
import com.example.hotel_reservation_system.dto.HotelResponse;
import com.example.hotel_reservation_system.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelReservationProperties properties;
    private final HotelService hotelService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable Long id) {
        return new ResponseEntity<>(hotelService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public Slice<HotelPagination> getAllHotels(@RequestParam(value = "offset", required = false) Integer offset,
                                                   @RequestParam(value = "limit", required = false) Integer limit) {
        return hotelService.getAll(PageRequest.of(Optional.ofNullable(offset).isPresent() ? offset : 0,
                Optional.ofNullable(limit).isPresent() ? limit : properties.paginationLimit()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> createHotel(@RequestBody @Valid HotelRequest request) {
        return new ResponseEntity<>(hotelService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable Long id, @RequestBody HotelRequest request) {
        return new ResponseEntity<>(hotelService.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<HotelResponse> deleteHotel(@PathVariable Long id) {
        hotelService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

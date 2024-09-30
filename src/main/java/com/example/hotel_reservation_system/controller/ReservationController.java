package com.example.hotel_reservation_system.controller;

import com.example.hotel_reservation_system.dto.ReservationRequest;
import com.example.hotel_reservation_system.dto.ReservationResponse;
import com.example.hotel_reservation_system.service.ReservationService;
import com.example.hotel_reservation_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;

    @GetMapping("/reservations")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Collection<ReservationResponse>> getAllReservations() {
        return new ResponseEntity<>(reservationService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/{id}/reservation")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<ReservationResponse> createReservation(@PathVariable Long id,
                                                                 @RequestBody @Valid ReservationRequest request,
                                                                 @AuthenticationPrincipal UserDetails userDetails) {
        request.setRoomId(id);
        request.setVisitorId(userService.getByUsername(userDetails.getUsername()).getId());
        return new ResponseEntity<>(reservationService.create(request), HttpStatus.CREATED);
    }
}

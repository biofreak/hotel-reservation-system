package com.example.hotel_reservation_system.service;

import com.example.hotel_reservation_system.dto.ReservationRequest;
import com.example.hotel_reservation_system.dto.ReservationResponse;
import com.example.hotel_reservation_system.dto.RoomResponse;
import com.example.hotel_reservation_system.dto.UserResponse;
import com.example.hotel_reservation_system.entity.Reservation;
import com.example.hotel_reservation_system.mapper.ReservationMapper;
import com.example.hotel_reservation_system.repository.ReservationRepository;
import com.example.hotel_reservation_system.repository.RoomRepository;
import com.example.hotel_reservation_system.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;

    public Collection<ReservationResponse> getAll() {
        return new ArrayList<Reservation>() {{ reservationRepository.findAll().forEach(this::add); }}
                .stream().map(reservationMapper::reservationToReservationResponse).toList();
    }

    public ReservationResponse create(ReservationRequest request) {
        return roomRepository.findById(request.getRoomId()).map(room -> {
            Reservation reservation = reservationMapper.reservationRequestToReservation(request);
            reservation.setRoom(room);
            return userRepository.findById(request.getVisitorId()).map(user -> {
                        reservation.setVisitor(user);
                        if(room.getOccupation().entrySet().stream()
                                .filter(entry -> entry.getValue().isBefore(request.getCheckIn()))
                                .filter(entry -> entry.getKey().isAfter(request.getCheckOut())).toList().isEmpty()) {
                            room.getOccupation().put(request.getCheckIn(), request.getCheckOut());
                            roomRepository.save(room);
                            return reservationMapper.reservationToReservationResponse(reservation);
                        } else {
                            throw new NotFoundException("Бронирование на указанный период невозможно," +
                                    " поскольку присутствует пересечение с оформленной бронью.");
                        }
                    }).orElseThrow(() ->
                            new NotFoundException("Пользователь с ID = '" + request.getVisitorId() + "' не найден."));
        }).orElseThrow(() -> new NotFoundException("Ккомната с ID = " + request.getRoomId() + " не найдена."));
    }
}

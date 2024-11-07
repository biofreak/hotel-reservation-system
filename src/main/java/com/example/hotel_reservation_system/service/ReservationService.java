package com.example.hotel_reservation_system.service;

import com.example.hotel_reservation_system.dto.ReservationRequest;
import com.example.hotel_reservation_system.dto.ReservationResponse;
import com.example.hotel_reservation_system.entity.Reservation;
import com.example.hotel_reservation_system.mapper.ReservationMapper;
import com.example.hotel_reservation_system.repository.ReservationRepository;
import com.example.hotel_reservation_system.repository.RoomRepository;
import com.example.hotel_reservation_system.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;
    private final StatisticsService statisticsService;

    public Collection<ReservationResponse> getAll() {
        return new ArrayList<Reservation>() {{ reservationRepository.findAll().forEach(this::add); }}
                .stream().map(reservationMapper::reservationToReservationResponse).toList();
    }

    public ReservationResponse create(ReservationRequest request) {
        return roomRepository.findById(request.getRoomId()).map(room -> {
            Reservation reservation = reservationMapper.reservationRequestToReservation(request);
            return userRepository.findById(request.getVisitorId()).map(user -> {
                        if(room.getOccupation().entrySet().stream()
                                .filter(entry -> entry.getValue().isBefore(request.getCheckIn()))
                                .filter(entry -> entry.getKey().isAfter(request.getCheckOut())).toList().isEmpty()) {
                            room.getOccupation().put(request.getCheckIn(), request.getCheckOut());
                            reservation.setVisitor(user);
                            reservation.setRoom(roomRepository.save(room));
                            Optional.of(reservationRepository.save(reservation))
                                    .ifPresent(statisticsService::sendReservation);
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

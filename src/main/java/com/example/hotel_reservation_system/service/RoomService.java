package com.example.hotel_reservation_system.service;

import com.example.hotel_reservation_system.dto.HotelResponse;
import com.example.hotel_reservation_system.dto.RoomRequest;
import com.example.hotel_reservation_system.dto.RoomResponse;
import com.example.hotel_reservation_system.entity.Hotel;
import com.example.hotel_reservation_system.entity.Room;
import com.example.hotel_reservation_system.mapper.HotelMapper;
import com.example.hotel_reservation_system.mapper.RoomMapper;
import com.example.hotel_reservation_system.repository.HotelRepository;
import com.example.hotel_reservation_system.repository.RoomRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final HotelService hotelService;
    private final RoomRepository roomRepository;
    private final HotelMapper hotelMapper;
    private final RoomMapper roomMapper;

    public RoomResponse getById(Long id) {
        return roomRepository.findById(id).map(roomMapper::roomToRoomResponse)
                .orElseThrow(() -> new NotFoundException("Ккомната с ID = " + id + " не найдена."));
    }

    public RoomResponse create(RoomRequest request) {
        return roomRepository.findByHotel_idAndNumber(request.getHotelId(), request.getNumber())
                .map(roomMapper::roomToRoomResponse).orElseGet(() -> {
                    Room room = roomMapper.roomRequestToRoom(request);
                    room.setHotel(hotelMapper.hotelResponseToHotel(hotelService.getById(request.getHotelId())));
                    return roomMapper.roomToRoomResponse(roomRepository.save(room));
                });
    }

    public RoomResponse update(Long id, RoomRequest request) {
        return roomRepository.findById(id)
                .map(room -> {
                    Room roomUpdate = roomMapper.roomRequestToRoom(request);
                    roomUpdate.setId(room.getId());
                    roomUpdate.setHotel(hotelMapper.hotelResponseToHotel(hotelService.getById(request.getHotelId())));
                    return roomUpdate;
                })
                .map(roomRepository::save)
                .map(roomMapper::roomToRoomResponse)
                .orElseThrow(() -> new NotFoundException("Комната с ID = '" + id + "' не найдена."));
    }

    public void delete(Long id) {
        roomRepository.findById(id).ifPresentOrElse(roomRepository::delete,
                () -> { throw new NotFoundException("Комната с ID = '" + id + "' не найдена."); });
    }
}

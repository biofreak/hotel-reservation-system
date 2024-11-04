package com.example.hotel_reservation_system.service;

import com.example.hotel_reservation_system.dto.*;
import com.example.hotel_reservation_system.entity.Room;
import com.example.hotel_reservation_system.mapper.HotelMapper;
import com.example.hotel_reservation_system.mapper.RoomMapper;
import com.example.hotel_reservation_system.repository.RoomRepository;
import com.example.hotel_reservation_system.specification.RoomSpecification;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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

    public Slice<RoomResponse> getFiltered(RoomFilter request, Pageable pageable) {
        List<RoomResponse> result = roomRepository.findAll(new RoomSpecification(new HashMap<>() {{
                    put("id", request.getId());
                    put("title", request.getTitle());
                    put("min", request.getMinPrice());
                    put("max", request.getMaxPrice());
                    put("guests", request.getNumberOfGuests());
                    put("in", request.getCheckIn());
                    put("out", request.getCheckIOut());
                    put("hotel_id", request.getHotelId());
                }}), pageable).stream()
                .map(roomMapper::roomToRoomResponse).toList();
        return new SliceImpl<>(result, pageable, result.iterator().hasNext());
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

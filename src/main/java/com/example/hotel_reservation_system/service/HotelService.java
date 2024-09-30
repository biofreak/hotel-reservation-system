package com.example.hotel_reservation_system.service;

import com.example.hotel_reservation_system.dto.HotelPagination;
import com.example.hotel_reservation_system.dto.HotelRequest;
import com.example.hotel_reservation_system.dto.HotelResponse;
import com.example.hotel_reservation_system.entity.Hotel;
import com.example.hotel_reservation_system.mapper.HotelMapper;
import com.example.hotel_reservation_system.repository.HotelRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@Named("HotelService")
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Named("getHotelResponse")
    public HotelResponse getById(Long id) {
        return hotelRepository.findById(id).map(hotelMapper::hotelToHotelResponse)
                .orElseThrow(() -> new NotFoundException("Отель с ID = " + id + " не найден."));
    }

    public Slice<HotelPagination> getAll(Pageable pageable) {
        List<HotelPagination> result = hotelRepository.findAll(pageable).stream()
                .map(hotelMapper::hotelToHotelPagination).toList();
        return new SliceImpl<>(result, pageable, result.iterator().hasNext());
    }

    public HotelResponse create(HotelRequest request) {
        return hotelRepository.findByTitleAndCity(request.getTitle(), request.getCity())
                .map(hotelMapper::hotelToHotelResponse).orElseGet(() ->
                    hotelMapper.hotelToHotelResponse(hotelRepository.save(hotelMapper.hotelRequestToHotel(request))));
    }

    public HotelResponse update(Long id, HotelRequest request) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    Hotel hotelUpdate = hotelMapper.hotelRequestToHotel(request);
                    hotelUpdate.setId(hotel.getId());
                    return hotelRepository.save(hotelUpdate);
                })
                .map(hotelMapper::hotelToHotelResponse)
                .orElseThrow(() -> new NotFoundException("Отель с ID = '" + id + "' не найден."));
    }

    public void delete(Long id) {
        hotelRepository.findById(id).ifPresentOrElse(hotelRepository::delete,
                () -> { throw new NotFoundException("Отель с ID = " + id + " не найден."); });
    }
}

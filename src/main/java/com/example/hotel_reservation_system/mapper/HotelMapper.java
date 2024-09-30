package com.example.hotel_reservation_system.mapper;

import com.example.hotel_reservation_system.dto.HotelPagination;
import com.example.hotel_reservation_system.dto.HotelRequest;
import com.example.hotel_reservation_system.dto.HotelResponse;
import com.example.hotel_reservation_system.entity.Hotel;
import com.example.hotel_reservation_system.service.HotelService;
import org.mapstruct.*;
import org.mapstruct.ap.internal.util.Services;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.FIELD,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@Named("HotelMapper")
public interface HotelMapper {
    HotelService hotelService = Services.get(HotelService.class, null);

    @Named("getHotelResponse")
    default HotelResponse getHotelResponse(Hotel hotel) {
        return Optional.ofNullable(hotel)
                .map(x -> hotelService.getById(x.getId()))
                .orElse(null);
    }

    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "caption", target = "caption"),
            @Mapping(source = "city", target = "city"),
            @Mapping(source = "address", target = "address"),
            @Mapping(source = "distance", target = "distance")
    })
    Hotel hotelRequestToHotel(HotelRequest hotelRequest);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "caption", target = "caption"),
            @Mapping(source = "city", target = "city"),
            @Mapping(source = "address", target = "address"),
            @Mapping(source = "distance", target = "distance"),
            @Mapping(source = "rating", target = "rating"),
            @Mapping(source = "scores", target = "scores")
    })

    HotelResponse hotelToHotelResponse(Hotel hotel);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "caption", target = "caption"),
            @Mapping(source = "city", target = "city"),
            @Mapping(source = "address", target = "address"),
            @Mapping(source = "distance", target = "distance")
    })
    Hotel hotelResponseToHotel(HotelResponse hotelResponse);

    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "city", target = "city"),
            @Mapping(source = "rating", target = "rating"),
            @Mapping(source = "scores", target = "scores")
    })
    HotelPagination hotelToHotelPagination(Hotel hotel);
}

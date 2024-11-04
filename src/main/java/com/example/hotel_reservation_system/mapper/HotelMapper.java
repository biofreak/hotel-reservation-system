package com.example.hotel_reservation_system.mapper;

import com.example.hotel_reservation_system.dto.HotelPagination;
import com.example.hotel_reservation_system.dto.HotelRequest;
import com.example.hotel_reservation_system.dto.HotelResponse;
import com.example.hotel_reservation_system.entity.Hotel;
import com.example.hotel_reservation_system.service.HotelService;
import org.mapstruct.*;

import java.util.Optional;
import java.util.ServiceLoader;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.FIELD,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@Named("HotelMapper")
public interface HotelMapper {
    HotelService hotelService = ServiceLoader.load(HotelService.class).findFirst().orElse(null);

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
            @Mapping(source = "numberOfRating", target = "numberOfRating")
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
            @Mapping(source = "numberOfRating", target = "numberOfRating")
    })
    HotelPagination hotelToHotelPagination(Hotel hotel);
}

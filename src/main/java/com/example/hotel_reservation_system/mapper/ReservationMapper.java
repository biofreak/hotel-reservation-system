package com.example.hotel_reservation_system.mapper;

import com.example.hotel_reservation_system.dto.ReservationRequest;
import com.example.hotel_reservation_system.dto.ReservationResponse;
import com.example.hotel_reservation_system.entity.Reservation;
import com.example.hotel_reservation_system.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                RoomMapper.class,
                UserMapper.class
        }
)
public interface ReservationMapper {
    @Mappings({
            @Mapping(source = "checkIn", target = "checkIn"),
            @Mapping(source = "checkOut", target = "checkOut"),
            @Mapping(target = "room", expression = "java(null)"),
            @Mapping(target = "visitor", expression = "java(null)")
    })
    Reservation reservationRequestToReservation(ReservationRequest reservationRequest);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "checkIn", target = "checkIn"),
            @Mapping(source = "checkOut", target = "checkOut"),
            @Mapping(source = "room", target = "room"),
            @Mapping(source = "visitor", target = "visitor")
    })
    ReservationResponse reservationToReservationResponse(Reservation reservation);
}

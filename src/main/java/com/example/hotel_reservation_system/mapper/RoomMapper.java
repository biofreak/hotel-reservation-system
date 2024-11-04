package com.example.hotel_reservation_system.mapper;

import com.example.hotel_reservation_system.dto.RoomRequest;
import com.example.hotel_reservation_system.dto.RoomResponse;
import com.example.hotel_reservation_system.entity.Room;
import org.mapstruct.*;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.FIELD,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                HotelMapper.class
        }
)
public interface RoomMapper {
    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "number", target = "number"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "capacity", target = "capacity"),
            @Mapping(target = "hotel", expression = "java(null)")
    })
    Room roomRequestToRoom(RoomRequest roomRequest);

    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "number", target = "number"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "capacity", target = "capacity"),
            @Mapping(target = "hotelId", expression = "java(room.getHotel().getId())")
    })
    RoomRequest roomToRoomRequest(Room room);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "number", target = "number"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "capacity", target = "capacity"),
            @Mapping(source = "occupation", target = "occupation"),
            @Mapping(source = "hotel", target = "hotel")
    })
    RoomResponse roomToRoomResponse(Room room);
}

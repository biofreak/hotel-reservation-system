package com.example.hotel_reservation_system.mapper;

import com.example.hotel_reservation_system.dto.UserRequest;
import com.example.hotel_reservation_system.dto.UserResponse;
import com.example.hotel_reservation_system.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "email", target = "email")
    })
    User userRequestToUser(UserRequest userRequest);

    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "email", target = "email")
    })
    UserRequest userToUserRequest(User user);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "role", target = "role")
    })
    UserResponse userToUserResponse(User user);
}

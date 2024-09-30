package com.example.hotel_reservation_system.dto;

import com.example.hotel_reservation_system.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRequest {
    @NotNull(message = "Для пользователя поле username должно быть заполнено.")
    private String username;
    private String password = "";
    @Email
    private String email;
}

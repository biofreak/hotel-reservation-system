package com.example.hotel_reservation_system.controller;

import com.example.hotel_reservation_system.dto.UserRequest;
import com.example.hotel_reservation_system.dto.UserResponse;
import com.example.hotel_reservation_system.entity.Role;
import com.example.hotel_reservation_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestParam @Valid Role role, @RequestBody @Valid UserRequest request) {
        return new ResponseEntity<>(userService.create(role, request), HttpStatus.CREATED);
    }
}

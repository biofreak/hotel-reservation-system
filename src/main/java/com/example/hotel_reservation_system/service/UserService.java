package com.example.hotel_reservation_system.service;

import com.example.hotel_reservation_system.dto.UserRequest;
import com.example.hotel_reservation_system.dto.UserResponse;
import com.example.hotel_reservation_system.entity.Role;
import com.example.hotel_reservation_system.entity.User;
import com.example.hotel_reservation_system.mapper.UserMapper;
import com.example.hotel_reservation_system.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final StatisticsService statisticsService;

    public Boolean usernameEmailExist(String username, String email) {
        return userRepository.existsByUsernameAndEmail(username, email);
    }

    public UserResponse getByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapper::userToUserResponse)
                .orElseThrow(() -> new NotFoundException("Пользователь '" + username + "' не найден."));
    }

    public UserResponse create(Role role, UserRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .map(userMapper::userToUserResponse).orElseGet(() -> {
                    User user  = userMapper.userRequestToUser(request);
                    user.setRole(role);
                    user = userRepository.save(user);
                    statisticsService.sendUser(user);
                    return userMapper.userToUserResponse(user);
                });
    }

    public UserResponse update(UserRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .map(user -> {
                    User userUpdate = userMapper.userRequestToUser(request);
                    userUpdate.setId(user.getId());
                    return userUpdate;
                })
                .map(userRepository::save)
                .map(userMapper::userToUserResponse)
                .orElseThrow(() -> new NotFoundException("Пользователь '" + request.getUsername() + "' не найден."));
    }

    public void delete(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete,
                () -> { throw new NotFoundException("Пользователь с ID = '" + id + "' не найден."); });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(userMapper::userToUserResponse).orElse(null);
    }
}

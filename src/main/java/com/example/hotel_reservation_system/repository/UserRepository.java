package com.example.hotel_reservation_system.repository;

import com.example.hotel_reservation_system.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsernameAndEmail(String username, String email);
}

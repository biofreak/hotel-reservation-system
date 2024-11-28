package com.example.hotel_reservation_system.aop;

import com.example.hotel_reservation_system.dto.UserRequest;
import com.example.hotel_reservation_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class UserAspect {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Around("execution(* com.example.hotel_reservation_system.controller.UserController.createUser(..))")
    public Object usernameAndEmailCheckToCreate(ProceedingJoinPoint joinPoint) throws Throwable {
        UserRequest request = (UserRequest) Arrays.asList(joinPoint.getArgs()).get(1);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return userService.usernameEmailExist(request.getUsername(),request.getEmail())? joinPoint: joinPoint.proceed();
    }
}

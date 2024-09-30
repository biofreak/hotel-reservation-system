package com.example.hotel_reservation_system.aop;

import com.example.hotel_reservation_system.dto.ReservationRequest;
import com.example.hotel_reservation_system.dto.UserRequest;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class ReservationAspect {
    @Around("execution(* com.example.hotel_reservation_system.controller.ReservationController.createReservation(..))")
    public Object usernameAndEmailCheckToCreate(ProceedingJoinPoint joinPoint) throws Throwable {
        ReservationRequest request = (ReservationRequest) Arrays.asList(joinPoint.getArgs()).get(1);
        if(request.validate()) {
            return joinPoint.proceed();
        } else throw new BadRequestException("Начало бронирования должно предшествовать окончанию бронирования.");
    }
}

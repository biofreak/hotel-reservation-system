package com.example.hotel_reservation_system.controller;

import com.example.hotel_reservation_system.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void getStatistics() {
        statisticsService.get();
    }
}

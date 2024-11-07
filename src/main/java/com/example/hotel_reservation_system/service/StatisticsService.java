package com.example.hotel_reservation_system.service;

import com.example.hotel_reservation_system.entity.Reservation;
import com.example.hotel_reservation_system.entity.User;
import com.example.hotel_reservation_system.event.UserEvent;
import com.example.hotel_reservation_system.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.hotel_reservation_system.event.ReservationEvent;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    @Value("${app.kafka.kafkaReservationTopic}")
    private String reservationTopicName;
    @Value("${app.kafka.kafkaUserTopic}")
    private String userTopicName;

    private final KafkaTemplate<String, ReservationEvent> kafkaReservationTemplate;
    private final KafkaTemplate<String, UserEvent> kafkaUserTemplate;
    private final StatisticsRepository statisticsRepository;

    public void sendReservation(Reservation reservation) {
        ReservationEvent event = ReservationEvent.builder()
                .userId(reservation.getVisitor().getId())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .build();
        kafkaReservationTemplate.send(reservationTopicName, new ObjectId().toString(), event);
    }

    public void sendUser(User user) {
        UserEvent event = UserEvent.builder().userId(user.getId()).build();
        kafkaUserTemplate.send(userTopicName, new ObjectId().toString(), event);
    }

    public void get() {
        statisticsRepository.getAll().getMappedResults().forEach(System.out::println);
    }
}

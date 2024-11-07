package com.example.hotel_reservation_system.service;

import com.example.hotel_reservation_system.entity.Reservation;
import com.example.hotel_reservation_system.entity.User;
import com.example.hotel_reservation_system.event.UserEvent;
import com.example.hotel_reservation_system.repository.StatisticsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.hotel_reservation_system.event.ReservationEvent;

import java.util.UUID;

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
        kafkaReservationTemplate.send(reservationTopicName, UUID.randomUUID().toString(), event);
    }

    public void sendUser(User user) {
        UserEvent event = UserEvent.builder().userId(user.getId()).build();
        kafkaUserTemplate.send(userTopicName, UUID.randomUUID().toString(), event);
    }

    public Resource get() {
        try {
            Resource resource = new FileSystemResource("statistics.csv");
            CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
            String json = statisticsRepository.getAll().getRawResults().toJson();
            JsonNode jsonTree = new ObjectMapper().readTree(json).get("results");
            JsonNode firstObject = jsonTree.elements().next();
            firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);
            CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
            new CsvMapper().writerFor(JsonNode.class).with(csvSchema).writeValue(resource.getFile(), jsonTree);
            return resource;
        } catch(Exception ignore) {}
        return null;
    }
}

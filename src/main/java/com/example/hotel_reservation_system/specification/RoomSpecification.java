package com.example.hotel_reservation_system.specification;

import com.example.hotel_reservation_system.entity.Room;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

public record RoomSpecification(Map<String, ? extends Comparable<?>> criteria) implements Specification<Room> {
    @Override
    public Predicate toPredicate(@Nullable Root<Room> root,
                                 @Nullable CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {
        return criteria.entrySet().stream()
                .filter(entry -> Optional.ofNullable(entry.getValue()).isPresent())
                .map(entry -> Optional.ofNullable(root).map(x ->
                    switch (entry.getKey()) {
                        case "id" -> builder.equal(x.get(entry.getKey()), entry.getValue());
                        case "title" -> builder.equal(x.get(entry.getKey()), entry.getValue());
                        case "min" -> builder.greaterThanOrEqualTo(x.get("price"), (Double) entry.getValue());
                        case "max" -> builder.lessThanOrEqualTo(x.get("price"), (Double) entry.getValue());
                        case "guests" -> builder.lessThanOrEqualTo(x.get("capacity"), (Integer) entry.getValue());
                        case "in" -> {
                            if(criteria.get("out") == null) yield builder.conjunction();
                            else {
                                MapJoin<Room, Instant, Instant> subRoot = x.joinMap("occupation");
                                var start = (Instant) entry.getValue();
                                var end = (Instant) criteria.get("out");
                                yield builder.not(
                                        builder.or(
                                                builder.and(
                                                        builder.lessThanOrEqualTo(subRoot.key(), start),
                                                        builder.greaterThanOrEqualTo(subRoot.value(), start)
                                                ),
                                                builder.and(
                                                        builder.lessThanOrEqualTo(subRoot.key(), end),
                                                        builder.greaterThanOrEqualTo(subRoot.value(), end)
                                                )
                                        )
                                );
                            }
                        }
                        case "hotel_id" -> builder.equal(x.get("hotel").get("id"), entry.getValue());
                        default -> builder.conjunction();
                    }
                ).orElse(null)).reduce(builder::and).orElse(null);
    }
}

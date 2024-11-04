package com.example.hotel_reservation_system.specification;

import com.example.hotel_reservation_system.entity.Hotel;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;
import java.util.Optional;

public record HotelSpecification(Map<String, ? extends Comparable<?>> criteria) implements Specification<Hotel> {
    @Override
    public Predicate toPredicate(@Nullable Root<Hotel> root,
                                 @Nullable CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {
        return criteria.entrySet().stream()
                .filter(entry -> Optional.ofNullable(entry.getValue()).isPresent())
                .map(entry ->
                        builder.equal(Optional.ofNullable(root).map(x -> x.get(entry.getKey())).orElse(null),
                            entry.getValue()))
                .reduce(builder::and).orElse(null);
    }
}

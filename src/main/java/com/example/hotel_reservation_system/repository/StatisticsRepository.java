package com.example.hotel_reservation_system.repository;

import com.example.hotel_reservation_system.entity.Statistics;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends MongoRepository<Statistics, String> {
    @Aggregation("{ '$unionWith': 'user' }")
    AggregationResults<Statistics> getAll();
}

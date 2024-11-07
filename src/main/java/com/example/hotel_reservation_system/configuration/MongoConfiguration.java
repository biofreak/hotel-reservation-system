package com.example.hotel_reservation_system.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.example.hotel_reservation_system.repository")
public class MongoConfiguration {
    @Value("${spring.data.mongodb.uri}")
    private String clientUri;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    MongoClient mongoClient() {
        return MongoClients.create(clientUri);
    }

    @Bean
    MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, database);
    }
}

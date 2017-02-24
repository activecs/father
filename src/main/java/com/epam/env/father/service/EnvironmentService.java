package com.epam.env.father.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.epam.env.father.model.Environment;
import com.epam.env.father.repository.EnvironmentRepository;

@Service
public class EnvironmentService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private EnvironmentRepository repository;

    public List<Environment> getAllAvailable() {
        return repository.findAll();
    }

    public List<String> getEnvironmentBanners() {
        String environmentCollection = mongoTemplate.getCollectionName(Environment.class);
        return mongoTemplate.getCollection(environmentCollection).distinct("country");
    }

    public List<Environment> getExpiredEnvironments() {
        return getExpiredEnvironments(LocalDateTime.now());
    }

    public List<Environment> getExpiredEnvironments(LocalDateTime expirationDateTime) {
        return repository.findByReservedByIsNotNullAndReservationExpirationLessThan(expirationDateTime);
    }

}

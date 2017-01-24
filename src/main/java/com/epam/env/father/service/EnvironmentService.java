package com.epam.env.father.service;

import com.epam.env.father.model.Client;
import com.epam.env.father.model.Environment;
import com.epam.env.father.repository.EnvironmentRepository;
import com.epam.env.father.service.validation.NotReserved;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
public class EnvironmentService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private EnvironmentRepository repository;
    @Value("${reservation.period.default.value}")
    private Long reservationPeriod;

    public Environment reserveEnvironment(@NotReserved String environmentId, Client client) {
        Environment environment = repository.findOne(environmentId);
        environment.setReservedBy(client);
        environment.setReservationExpiration(LocalDateTime.now().plusMinutes(reservationPeriod));
        return repository.save(environment);
    }

    public List<String> getCountries() {
        return mongoTemplate.getCollection(mongoTemplate.getCollectionName(Environment.class)).distinct("country");
    }

    public void releaseReservations(Client client) {
        repository.findByReservedBy(client).forEach(environment -> {
            environment.setReservationExpiration(LocalDateTime.now());
            repository.save(environment);
        });
    }

    public List<Environment> getAllAvailable() {
        return repository.findAll();
    }

}

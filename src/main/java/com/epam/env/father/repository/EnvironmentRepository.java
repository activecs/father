package com.epam.env.father.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.epam.env.father.model.Client;
import com.epam.env.father.model.Environment;

public interface EnvironmentRepository extends MongoRepository<Environment, String> {

    List<Environment> findByReservedByIsNotNullAndReservationExpirationLessThan(LocalDateTime reservationExpiration);

    List<Environment> findByReservedBy(Client client);

    List<Environment> findByReservedByIsNullAndCountry(String country);
}

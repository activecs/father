package com.epam.env.father.repository;

import com.epam.env.father.model.Client;
import com.epam.env.father.model.Environment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EnvironmentRepository extends MongoRepository<Environment, String> {

    List<Environment> findByReservationExpirationLessThanAndReservedByIsNotNull(LocalDateTime reservationExpiration);

    List<Environment> findByReservedBy(Client client);
}

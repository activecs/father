package com.epam.env.father.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.epam.env.father.model.Client;
import org.springframework.data.mongodb.repository.Query;

public interface ClientRepository extends MongoRepository<Client, Integer> {

    Client findByChatId(String id);

    Client findByFirstNameAndLastName(String firstName, String lastName);
}

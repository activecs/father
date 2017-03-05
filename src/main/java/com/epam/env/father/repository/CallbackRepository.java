package com.epam.env.father.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.epam.env.father.model.Callback;

public interface CallbackRepository extends MongoRepository<Callback, String> {

}

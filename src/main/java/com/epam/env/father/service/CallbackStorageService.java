package com.epam.env.father.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.env.father.data.AbstractCallBackData;
import com.epam.env.father.model.Callback;
import com.epam.env.father.repository.CallbackRepository;

@Service
public class CallbackStorageService {

    @Autowired
    private CallbackRepository callbackRepository;

    public Callback save(AbstractCallBackData callbackData) {
        return callbackRepository.save(new Callback(callbackData));
    }

    public Optional<Callback> findByKey(String key) {
        return Optional.ofNullable(callbackRepository.findOne(key));
    }

}

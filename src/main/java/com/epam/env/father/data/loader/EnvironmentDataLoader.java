package com.epam.env.father.data.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.epam.env.father.model.Environment;
import com.epam.env.father.model.Environment.EnvironmentBuilder;
import com.epam.env.father.repository.EnvironmentRepository;

@Component
public class EnvironmentDataLoader implements ApplicationRunner {

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Override
    public void run(ApplicationArguments args) {
        environmentRepository.deleteAll();
        EnvironmentBuilder environmentBuilder = Environment.builder();
        environmentRepository.insert(environmentBuilder.id("be-dev1").build());
        environmentRepository.insert(environmentBuilder.id("be-dev2").build());
        environmentRepository.insert(environmentBuilder.id("gr-dev1").build());
        environmentRepository.insert(environmentBuilder.id("gr-dev2").build());
    }
}

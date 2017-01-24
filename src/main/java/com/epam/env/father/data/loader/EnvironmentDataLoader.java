package com.epam.env.father.data.loader;

import com.epam.env.father.model.Environment;
import com.epam.env.father.model.Environment.EnvironmentBuilder;
import com.epam.env.father.repository.EnvironmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentDataLoader implements ApplicationRunner {

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Override
    public void run(ApplicationArguments args) {
        environmentRepository.deleteAll();
        EnvironmentBuilder environmentBuilder = Environment.builder();
        Environment beDev1 = environmentBuilder.id("be-dev1").country("be").build();
        Environment beDev2 = environmentBuilder.id("be-dev2").country("be").build();
        Environment grDev1 = environmentBuilder.id("gr-dev1").country("gr").build();
        Environment grDev2 = environmentBuilder.id("gr-dev2").country("gr").build();
        Environment roDev1 = environmentBuilder.id("ro-dev1").country("ro").build();
        Environment roDev2 = environmentBuilder.id("ro-dev2").country("ro").build();
        environmentRepository.insert(beDev1);
        environmentRepository.insert(beDev2);
        environmentRepository.insert(grDev1);
        environmentRepository.insert(grDev2);
        environmentRepository.insert(roDev1);
        environmentRepository.insert(roDev2);
    }
}

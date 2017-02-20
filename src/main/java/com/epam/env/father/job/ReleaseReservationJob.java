package com.epam.env.father.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.epam.env.father.service.EnvironmentService;

@Service
public class ReleaseReservationJob {

    @Autowired
    private EnvironmentService environmentService;

    @Scheduled(cron = "0 * * * * *")
    public void start() {
        environmentService.getExpiredEnvironments().forEach(environmentService::releaseReservation);
    }

}

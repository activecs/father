package com.epam.env.father.job;

import static java.time.LocalDateTime.now;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.epam.env.father.event.ReleasingEnvironmentReminderEvent;
import com.epam.env.father.service.EnvironmentReservationService;
import com.epam.env.father.service.EnvironmentService;

@Service
public class ReleaseReservationJob {

    @Autowired
    private EnvironmentService environmentService;
    @Autowired
    private EnvironmentReservationService reservationService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Value("${reservation.expiration.reminder.default.value}")
    private Long expirationReminderPeriod;

    @Scheduled(cron = "0 * * * * *")
    public void releaseExpiredReservations() {
        environmentService.getExpiredEnvironments().forEach(reservationService::releaseReservation);
    }

    @Scheduled(cron = "0 * * * * *")
    public void sendReminderAboutSoonReleasing() {
        environmentService
                .getExpiredEnvironments(now().plusMinutes(expirationReminderPeriod)).stream()
                .map(ReleasingEnvironmentReminderEvent::new)
                .forEach(eventPublisher::publishEvent);
    }

}

package com.epam.env.father.job;

import static com.epam.env.father.model.ClientNotificationType.SOON_RELEASE;
import static java.time.LocalDateTime.now;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.epam.env.father.data.booking.ClientNotificationData;
import com.epam.env.father.event.ReleasingEnvironmentReminderEvent;
import com.epam.env.father.model.Environment;
import com.epam.env.father.service.ClientNotificationService;
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
    @Autowired
    private ClientNotificationService notificationService;
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
                .filter(this::isNotificationNeeded)
                .map(ReleasingEnvironmentReminderEvent::new)
                .forEach(eventPublisher::publishEvent);
    }

    private boolean isNotificationNeeded(Environment environment) {
        ClientNotificationData notificationData = new ClientNotificationData();
        notificationData.setClient(environment.getReservedBy());
        notificationData.setEnvironment(environment);
        notificationData.setNotificationType(SOON_RELEASE);
        return !notificationService.isNotificationSent(notificationData);
    }

}

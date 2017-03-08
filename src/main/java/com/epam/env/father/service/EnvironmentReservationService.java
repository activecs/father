package com.epam.env.father.service;

import static com.epam.env.father.model.ClientNotificationType.SOON_RELEASE;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.epam.env.father.data.booking.ClientNotificationData;
import com.epam.env.father.event.ReleasedEnvironmentEvent;
import com.epam.env.father.model.Client;
import com.epam.env.father.model.Environment;
import com.epam.env.father.repository.EnvironmentRepository;
import com.epam.env.father.service.validation.NotReserved;
import com.google.common.collect.Sets;
import com.rits.cloning.Cloner;

@Service
@Validated
public class EnvironmentReservationService {

    @Autowired
    private EnvironmentRepository repository;
    @Value("${reservation.period.default.value}")
    private Long reservationPeriod;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private ClientNotificationService clientNotificationService;
    @Autowired
    private Cloner cloner;

    public Set<Long> getPossibleReservationPeriods() {
        return Sets.newHashSet(30L,60L,90L);
    }

    public Environment reserveEnvironment(@NotReserved String environmentId, Client client) {
        return reserveEnvironment(environmentId, client, reservationPeriod);
    }

    public Environment reserveEnvironment(@NotReserved String environmentId, Client client, Long reservationPeriod) {
        Environment environment = repository.findOne(environmentId);
        environment.setReservedBy(client);
        environment.setReservationExpiration(LocalDateTime.now().plusMinutes(reservationPeriod));
        return repository.save(environment);
    }

    public void releaseReservations(Client client) {
        repository.findByReservedBy(client).forEach(environment -> {
            environment.setReservationExpiration(LocalDateTime.now());
            repository.save(environment);
        });
    }

    public void releaseReservation(Environment environment) {
        Environment eventEnvironmentCopy = cloner.deepClone(environment);
        clearReservationDataFromClient(environment);
        clearReservationDataFromEnvironment(environment);
        eventPublisher.publishEvent(new ReleasedEnvironmentEvent(eventEnvironmentCopy));
    }

    private void clearReservationDataFromClient(Environment environment) {
        ClientNotificationData notificationData = new ClientNotificationData();
        notificationData.setEnvironment(environment);
        notificationData.setClient(environment.getReservedBy());
        notificationData.setNotificationType(SOON_RELEASE);
        clientNotificationService.releaseNotification(notificationData);
    }

    private void clearReservationDataFromEnvironment(Environment environment) {
        environment.setReservedBy(null);
        environment.setReservationExpiration(null);
        repository.save(environment);
    }

}

package com.epam.env.father.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.env.father.data.booking.ClientNotificationData;
import com.epam.env.father.model.Client;
import com.epam.env.father.model.ClientNotificationStatus;
import com.epam.env.father.model.ClientNotificationType;

@Service
public class ClientNotificationService {

    @Autowired
    private ClientService clientService;

    public boolean isNotificationSent(ClientNotificationData notificationData) {
        String environmentId = notificationData.getEnvironment().getId();
        ClientNotificationStatus notificationStatus = notificationData.getClient().getNotificationStatus();
        return notificationStatus.getExpirationReminder().getOrDefault(environmentId, false);
    }

    public void submitNotification(ClientNotificationData notificationData) {
        Client client = notificationData.getClient();
        ClientNotificationStatus notificationStatus = client.getNotificationStatus();
        ClientNotificationType notificationType = notificationData.getNotificationType();
        notificationStatus.getExpirationReminder().compute(notificationType, (clientNotificationType, isSent) -> true);
        clientService.update(client);
    }

    public void releaseNotification(ClientNotificationData notificationData) {
        Client client = notificationData.getClient();
        ClientNotificationStatus notificationStatus = client.getNotificationStatus();
        ClientNotificationType notificationType = notificationData.getNotificationType();
        notificationStatus.getExpirationReminder().compute(notificationType, (clientNotificationType, isSent) -> false);
        clientService.update(client);
    }

}

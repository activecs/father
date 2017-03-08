package com.epam.env.father.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class ClientNotificationStatus {
    @Getter
    @Setter
    private Map<ClientNotificationType, Boolean> expirationReminder;

}

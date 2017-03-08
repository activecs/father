package com.epam.env.father.data.booking;

import com.epam.env.father.event.ReleasingEnvironmentReminderEvent;
import com.epam.env.father.model.Client;
import com.epam.env.father.model.ClientNotificationType;
import com.epam.env.father.model.Environment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ClientNotificationData {

    @Getter
    @Setter
    private Client client;
    @Getter
    @Setter
    private Environment environment;
    @Getter
    @Setter
    private ClientNotificationType notificationType;

    public ClientNotificationData(ReleasingEnvironmentReminderEvent reminderEvent) {
        this.client = reminderEvent.getEnvironment().getReservedBy();
        this.environment = reminderEvent.getEnvironment();
        this.notificationType = ClientNotificationType.SOON_RELEASE;
    }

}

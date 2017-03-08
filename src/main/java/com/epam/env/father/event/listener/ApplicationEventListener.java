package com.epam.env.father.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import com.epam.env.father.data.booking.ClientNotificationData;
import com.epam.env.father.data.builder.SendMessageBuilder;
import com.epam.env.father.event.ReleasedEnvironmentEvent;
import com.epam.env.father.event.ReleasingEnvironmentReminderEvent;
import com.epam.env.father.model.Client;
import com.epam.env.father.model.Environment;
import com.epam.env.father.service.ChatNotificationService;
import com.epam.env.father.service.ClientNotificationService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ApplicationEventListener {

    @Autowired
    private ChatNotificationService chatNotificationService;
    @Autowired
    private ClientNotificationService clientNotificationService;
    @Autowired
    private SendMessageBuilder sendMessageBuilder;

    @EventListener
    public void onApplicationEvent(ReleasedEnvironmentEvent event) {
        Environment environment = event.getEnvironment();
        Client client = environment.getReservedBy();
        SendMessage sendMessage = sendMessageBuilder.begin()
                .withLocale(client.getLocale())
                .withChatId(client.getId().toString())
                .withMessage("reservation.was.released")
                .withMessageArg(environment.getCountry())
                .withMessageArg(environment.getId())
                .build();
        chatNotificationService.sendMessageToChat(sendMessage);
    }

    @EventListener
    public void onApplicationEvent(ReleasingEnvironmentReminderEvent event) {
        Environment environment = event.getEnvironment();
        Client client = environment.getReservedBy();
        SendMessage sendMessage = sendMessageBuilder.begin()
                .withLocale(client.getLocale())
                .withChatId(client.getId().toString())
                .withMessage("reservation.will.be.released")
                .withMessageArg(environment.getCountry())
                .withMessageArg(environment.getId())
                .withMessageArg(environment.getReservationExpiration())
                .build();
        chatNotificationService.sendMessageToChat(sendMessage);
        clientNotificationService.submitNotification(new ClientNotificationData(event));
    }

}

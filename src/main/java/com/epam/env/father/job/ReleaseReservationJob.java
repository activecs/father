package com.epam.env.father.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import com.epam.env.father.service.ChatNotificationService;
import com.epam.env.father.service.EnvironmentService;

@Service
public class ReleaseReservationJob {

    @Autowired
    private EnvironmentService environmentService;
    @Autowired
    private ChatNotificationService chatNotificationService;

    @Scheduled(cron = "0 * * * * *")
    public void releaseExpiredReservations() {
        environmentService.getExpiredEnvironments().forEach(environmentService::releaseReservation);
    }

    //@Scheduled(cron = "0 * * * * *")
    public void sendReminderAboutSoonReleasing() {
        SendMessage sendMessage = new SendMessage();
        chatNotificationService.sendMessageToChat(sendMessage);

    }

}

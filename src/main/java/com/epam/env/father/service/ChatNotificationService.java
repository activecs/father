package com.epam.env.father.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;

import lombok.SneakyThrows;

@Service
public class ChatNotificationService {

    @Autowired
    private AbsSender fatherBot;

    @SneakyThrows
    public void sendMessageToChat(SendMessage sendMessage) {
        fatherBot.sendMessage(sendMessage);
    }

}

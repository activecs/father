package com.epam.env.father.bot.message.processor;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import lombok.SneakyThrows;

@Component
public class TextMessageProcessor {

    @SneakyThrows
    public SendMessage process(Update update) {
        SendMessage message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(update.getMessage().getText());
        return message;
    }

}

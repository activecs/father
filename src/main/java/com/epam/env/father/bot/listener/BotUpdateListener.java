package com.epam.env.father.bot.listener;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public interface BotUpdateListener<T> {

    SendMessage process(Update update, T data);

}

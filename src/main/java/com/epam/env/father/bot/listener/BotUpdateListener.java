package com.epam.env.father.bot.listener;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import com.epam.env.father.data.AbstractCallBackData;

public interface BotUpdateListener<T extends AbstractCallBackData> {

    SendMessage process(Update update, T data);

}

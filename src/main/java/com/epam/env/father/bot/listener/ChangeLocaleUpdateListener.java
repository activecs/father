package com.epam.env.father.bot.listener;

import org.telegram.telegrambots.api.objects.Update;

import com.epam.env.father.bot.meta.UpdateListenerComponent;

import lombok.extern.log4j.Log4j2;

@Log4j2
@UpdateListenerComponent
public class ChangeLocaleUpdateListener implements BotUpdateListener {


    @Override
    public int processCallbackQuery(Update update) {
        log.info("Got update query" + update);
        return CONFIRMED_NOT_UPDATES_ALL;
    }
}

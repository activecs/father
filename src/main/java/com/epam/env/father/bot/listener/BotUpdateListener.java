package com.epam.env.father.bot.listener;

import org.telegram.telegrambots.api.objects.Update;

public interface BotUpdateListener {

    Integer CONFIRMED_UPDATES_ALL = -1;
    Integer CONFIRMED_NOT_UPDATES_ALL = 1;

    default int process(Update update) {
        if (update.hasCallbackQuery())
            return processCallbackQuery(update);
        return CONFIRMED_NOT_UPDATES_ALL;
    }

    int processCallbackQuery(Update update);

}

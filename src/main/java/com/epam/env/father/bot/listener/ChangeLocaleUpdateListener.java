package com.epam.env.father.bot.listener;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import com.epam.env.father.bot.meta.UpdateListenerComponent;
import com.epam.env.father.data.LocaleData;
import com.epam.env.father.model.Client;
import com.epam.env.father.service.ClientService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@UpdateListenerComponent
public class ChangeLocaleUpdateListener implements BotUpdateListener<LocaleData> {

    @Autowired
    private ClientService clientService;

    @Override
    public SendMessage process(Update update, LocaleData data) {
        Client client = clientService.findById(update.getCallbackQuery().getFrom().getId()).get();
        client.setLocale(new Locale(data.getLanguage()));
        clientService.update(client);
        return new SendMessage()
                .setChatId(client.getId().toString())
                .setText("locale was changed to " + data.getLanguage());
    }
}

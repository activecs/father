package com.epam.env.father.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.model.Client;
import com.epam.env.father.service.EnvironmentService;

@CommandComponent
public class StopCommand extends TexteReplyBotCommand {

    @Autowired
    private EnvironmentService environmentService;

    public StopCommand() {
        super("stop", "With this command you can stop the Bot");
    }

    @Override
    protected void prepareResponse(SendMessage answer, AbsSender absSender, Client user) throws TelegramApiException {
        String message = messageSource.getMessage("good.bye", new Object[]{user.getFirstName(), user.getLastName()}, user.getLocale());
        answer.setText(message);
        environmentService.releaseReservations(user);
        absSender.sendMessage(answer);
    }
}
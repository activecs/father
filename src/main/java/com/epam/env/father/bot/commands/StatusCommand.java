package com.epam.env.father.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.model.Client;
import com.epam.env.father.service.EnvironmentService;

@CommandComponent
public class StatusCommand extends TexteReplyBotCommand {

    @Autowired
    private EnvironmentService environmentService;

    public StatusCommand() {
        super("status", "With this command you can get up to date info regarding envs statuses");
    }

    @Override
    protected void prepareResponse(SendMessage answer, AbsSender absSender, Client user) throws TelegramApiException {
        answer.setText(environmentService.getAllAvailable().toString());
        absSender.sendMessage(answer);
    }
}

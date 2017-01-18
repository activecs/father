package com.epam.env.father.bot.commands;

import static java.lang.System.lineSeparator;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.data.builder.SendMessageBuilder;
import com.epam.env.father.model.Client;
import com.epam.env.father.model.Environment;
import com.epam.env.father.service.EnvironmentService;

@CommandComponent
public class StatusCommand extends TexteReplyBotCommand {

    @Autowired
    private EnvironmentService environmentService;

    public StatusCommand() {
        super("status", "With this command you can get up to date info regarding envs statuses");
    }

    @Override
    protected void sendResponse(SendMessageBuilder answer, AbsSender absSender, Client user) throws TelegramApiException {
        String envStatus = environmentService.getAllAvailable().stream().map(Environment::toString).collect(Collectors.joining(lineSeparator()));
        answer.withMessage(envStatus);
        absSender.sendMessage(answer.build());
    }

}

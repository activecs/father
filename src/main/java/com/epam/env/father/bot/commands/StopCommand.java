package com.epam.env.father.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.data.builder.SendMessageBuilder;
import com.epam.env.father.model.Client;
import com.epam.env.father.service.EnvironmentReservationService;

@CommandComponent
public class StopCommand extends TexteReplyBotCommand {

    @Autowired
    private EnvironmentReservationService reservationService;

    public StopCommand() {
        super("stop", "With this command you can stop the Bot");
    }

    @Override
    protected void sendResponse(SendMessageBuilder answer, AbsSender absSender, Client user) throws TelegramApiException {
        answer.withMessage("good.bye")
              .withMessageArg(user.getFirstName())
              .withMessageArg(user.getLastName());
        reservationService.releaseReservations(user);
        absSender.sendMessage(answer.build());
    }
}

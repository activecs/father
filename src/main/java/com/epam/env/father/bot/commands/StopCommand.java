package com.epam.env.father.bot.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.model.Client;

@CommandComponent
public class StopCommand extends TexteReplyBotCommand {

    public StopCommand() {
        super("stop", "With this command you can stop the Bot");
    }

    @Override
    protected void prepareResponse(SendMessage answer, AbsSender absSender, Client user) throws TelegramApiException {
        String userName = user.getFirstName() + " " + user.getLastName();
        answer.setText("Good bye " + userName + "\n" + "Hope to see you soon!");
        absSender.sendMessage(answer);
    }
}

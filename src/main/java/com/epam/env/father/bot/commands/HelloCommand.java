package com.epam.env.father.bot.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.model.Client;

@CommandComponent
public class HelloCommand extends TexteReplyBotCommand {

    public HelloCommand() {
        super("hello", "Say hallo to this bot");
    }

    @Override
    protected void prepareResponse(SendMessage answer, AbsSender absSender, Client user) throws TelegramApiException {
        String userName = user.getFirstName() + " " + user.getLastName();
        StringBuilder messageTextBuilder = new StringBuilder("Hello ").append(userName);
        answer.setText(messageTextBuilder.toString());
        absSender.sendMessage(answer);
    }
}
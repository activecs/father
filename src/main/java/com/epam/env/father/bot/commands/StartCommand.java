package com.epam.env.father.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.data.builder.ReplyKeyboardMarkupBuilder;
import com.epam.env.father.data.builder.SendMessageBuilder;
import com.epam.env.father.model.Client;

@CommandComponent
public class StartCommand extends TexteReplyBotCommand {

    @Autowired
    private ReplyKeyboardMarkupBuilder replyKeyboardMarkupBuilder;

    public StartCommand() {
        super("start", "With this command you can start the Bot");
    }

    @Override
    protected void sendResponse(SendMessageBuilder answer, AbsSender absSender, Client user) throws TelegramApiException {
        answer.withMessage("welcome")
                .withMessageArg(user.getFirstName())
                .withMessageArg(user.getLastName())
                .withReplyKeyboard(replyKeyboardMarkupBuilder
                        .withFirstRowComand("/status")
                        .withResizeKeyboard(true)
                        .withSelective(true));
        absSender.sendMessage(answer.build());
    }
}
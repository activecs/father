package com.epam.env.father.bot.commands;

import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.model.Client;

@CommandComponent
public class LocaleCommand extends TexteReplyBotCommand {

    public LocaleCommand() {
        super("locale", "With this command you can change your locale");
    }

    @Override
    protected void prepareResponse(SendMessage answer, AbsSender absSender, Client user) throws TelegramApiException {
        AnswerCallbackQuery inlineQuery = new AnswerCallbackQuery();
        inlineQuery.setText("Please select locale");
        absSender.answerCallbackQuery(inlineQuery);
    }
}

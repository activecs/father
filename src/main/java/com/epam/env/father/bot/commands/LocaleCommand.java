package com.epam.env.father.bot.commands;

import static java.util.Locale.ENGLISH;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.data.LocaleData;
import com.epam.env.father.data.builder.InlineKeyboardButtonBuilder;
import com.epam.env.father.data.builder.InlineKeyboardMarkupBuilder;
import com.epam.env.father.data.builder.SendMessageBuilder;
import com.epam.env.father.model.Client;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.SneakyThrows;

@CommandComponent
public class LocaleCommand extends TexteReplyBotCommand {

    @Autowired
    private InlineKeyboardButtonBuilder buttonBuilder;
    @Autowired
    private InlineKeyboardMarkupBuilder markupBuilder;

    public LocaleCommand() {
        super("locale", "With this command you can change your locale");
    }

    @SneakyThrows
    @Override
    protected void sendResponse(SendMessageBuilder answer, AbsSender absSender, Client user) throws TelegramApiException {
        answer.withMessage("please.select.locale")
                .withReplyKeyboard(markupBuilder.begin()
                        .withLocale(user.getLocale())
                        .withFistLineButton(createButton("select.locale.ukr", new Locale("ukr")))
                        .withFistLineButton(createButton("select.locale.en", ENGLISH))
                        .withFistLineButton(createButton("select.locale.ru", new Locale("ru"))));
        absSender.sendMessage(answer.build());
    }

    private InlineKeyboardButtonBuilder createButton(String message, Locale locale) throws JsonProcessingException {
        return buttonBuilder.begin()
                .withData(new LocaleData(locale.getLanguage()))
                .withMessage(message);
    }
}

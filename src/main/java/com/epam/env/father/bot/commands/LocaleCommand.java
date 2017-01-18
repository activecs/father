package com.epam.env.father.bot.commands;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.data.LocaleData;
import com.epam.env.father.data.builder.InlineKeyboardButtonBuilder;
import com.epam.env.father.model.Client;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.SneakyThrows;

@CommandComponent
public class LocaleCommand extends TexteReplyBotCommand {

    @Autowired
    private InlineKeyboardButtonBuilder keyboardButtonBuilder;

    public LocaleCommand() {
        super("locale", "With this command you can change your locale");
    }

    @SneakyThrows
    @Override
    protected void prepareResponse(SendMessage answer, AbsSender absSender, Client user) throws TelegramApiException {
        //TODO: replace with InlineKeyboardMarkupBuilder
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton ukrLocaleButton = createButton("select.locale.ukr", new Locale("ukr"));
        InlineKeyboardButton enLocaleButton = createButton("select.locale.en", ENGLISH);
        InlineKeyboardButton ruLocaleButton = createButton("select.locale.ru", new Locale("ru"));
        List<InlineKeyboardButton> firstLine = newArrayList(ukrLocaleButton, enLocaleButton, ruLocaleButton);
        keyboardMarkup.setKeyboard(asList(firstLine));
        answer.setReplyMarkup(keyboardMarkup);
        answer.setText("please.select.locale");
        absSender.sendMessage(answer);
    }

    private InlineKeyboardButton createButton(String message, Locale locale) throws JsonProcessingException {
        return keyboardButtonBuilder.begin()
                .withData(new LocaleData(locale.getLanguage()))
                .withMessage(message)
                .build();
    }
}

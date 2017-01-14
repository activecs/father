package com.epam.env.father.bot.commands;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton enLocaleButton = new InlineKeyboardButton();
        enLocaleButton.setText("select.locale.en");
        enLocaleButton.setCallbackData("locale=en");
        List<InlineKeyboardButton> firstLine = newArrayList(enLocaleButton);
        keyboardMarkup.setKeyboard(asList(firstLine));
        answer.setReplyMarkup(keyboardMarkup);
        answer.setText("please.select.locale");
        absSender.sendMessage(answer);
    }
}

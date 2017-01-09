package com.epam.env.father.bot.commands;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.model.Client;

@CommandComponent
public class StartCommand extends TexteReplyBotCommand {

    public StartCommand() {
        super("start", "With this command you can start the Bot");
    }

    @Override
    protected void prepareResponse(SendMessage answer, AbsSender absSender, Client user) throws TelegramApiException {
        String message = messageSource.getMessage("welcome", new Object[]{user.getFirstName(), user.getLastName()}, user.getLocale());
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> commands = new ArrayList<>();
        KeyboardRow commandRow = new KeyboardRow();
        commandRow.add("/status");
        commands.add(commandRow);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(commands);
        replyKeyboardMarkup.setSelective(true);
        answer.setText(message);
        answer.setReplyMarkup(replyKeyboardMarkup);
        absSender.sendMessage(answer);
    }
}
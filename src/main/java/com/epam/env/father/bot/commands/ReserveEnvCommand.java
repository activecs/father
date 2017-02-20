package com.epam.env.father.bot.commands;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.data.builder.InlineKeyboardButtonBuilder;
import com.epam.env.father.data.builder.InlineKeyboardMarkupBuilder;
import com.epam.env.father.data.builder.SendMessageBuilder;
import com.epam.env.father.model.Client;
import com.epam.env.father.service.EnvironmentService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@CommandComponent
public class ReserveEnvCommand extends TexteReplyBotCommand {

    @Autowired
    private InlineKeyboardButtonBuilder buttonBuilder;
    @Autowired
    private InlineKeyboardMarkupBuilder markupBuilder;
    @Autowired
    private EnvironmentService environmentService;

    public ReserveEnvCommand() {
        super("reserve", "With this command you can choose an env to be booked");
    }

    @SneakyThrows
    @Override
    protected void sendResponse(SendMessageBuilder answer, AbsSender absSender, Client user) throws TelegramApiException {
        InlineKeyboardMarkupBuilder inlineKeyboardMarkupBuilder = markupBuilder.begin().withLocale(user.getLocale());
        environmentService.getEnvironmentBanners().forEach(country -> inlineKeyboardMarkupBuilder.withFistLineButton(createButton(country, country)));
        absSender.sendMessage(answer.withMessage("please.select.country")
                .withReplyKeyboard(inlineKeyboardMarkupBuilder).build());
    }

    private InlineKeyboardButtonBuilder createButton(String message, String country) {
        return buttonBuilder.begin()
                .withData(country)
                .withMessage(message);
    }
}

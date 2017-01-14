package com.epam.env.father.bot;

import static com.epam.env.father.bot.listener.BotUpdateListener.CONFIRMED_UPDATES_ALL;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import com.epam.env.father.bot.listener.BotUpdateListener;
import com.epam.env.father.bot.meta.BotName;
import com.epam.env.father.bot.meta.Command;
import com.epam.env.father.bot.meta.UpdateListener;

import javaslang.control.Try;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class EnvFatherBotConfig extends TelegramLongPollingCommandBot {

    @Command(BotName.ENV_FATHER)
    @Autowired
    private List<BotCommand> commands;
    @UpdateListener(BotName.ENV_FATHER)
    @Autowired
    private List<BotUpdateListener> updateListeners;
    @Value("${bot.token}")
    private String botToken;
    @Autowired
    private TelegramBotsApi botsApi;
    @Value("${bot.id}")
    private String botUsername;

    @PostConstruct
    public void init() throws TelegramApiRequestException {
        botsApi.registerBot(this);
        registerAll(commands.toArray(new BotCommand[commands.size()]));
    }


    @Override
    public void processNonCommandUpdate(Update update) {
        Try.of(() -> processUpdateRequest(update))
            .filter((requestIsHandled) -> !requestIsHandled)
            .andThenTry(() -> handleTextMessage(update));
    }

    @SneakyThrows
    private void handleTextMessage(Update update) {
        SendMessage message = new SendMessage()
            .setChatId(update.getMessage().getChatId())
            .setText(update.getMessage().getText());
        sendMessage(message);
    }

    private boolean processUpdateRequest(Update update) {
        return updateListeners.stream()
                .map(listener -> listener.process(update))
                .filter(CONFIRMED_UPDATES_ALL::equals)
                .findAny()
                .isPresent();
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }



}

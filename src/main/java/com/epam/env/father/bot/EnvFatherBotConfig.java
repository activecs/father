package com.epam.env.father.bot;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import com.epam.env.father.bot.message.BotUpdateMessageRouter;
import com.epam.env.father.bot.meta.BotName;
import com.epam.env.father.bot.meta.Command;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class EnvFatherBotConfig extends TelegramLongPollingCommandBot {

    @Command(BotName.ENV_FATHER)
    @Autowired
    private List<BotCommand> commands;
    @Value("${bot.token}")
    private String botToken;
    @Autowired
    private TelegramBotsApi botsApi;
    @Value("${bot.id}")
    private String botUsername;
    @Autowired
    private BotUpdateMessageRouter messageRouter;

    @PostConstruct
    public void init() throws TelegramApiRequestException {
        botsApi.registerBot(this);
        registerAll(commands.toArray(new BotCommand[commands.size()]));
    }

    @SneakyThrows
    @Override
    public void processNonCommandUpdate(Update update) {
        sendMessage(messageRouter.process(update));
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

package com.epam.env.father.bot.commands;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.model.Client;
import com.epam.env.father.service.ClientService;

import lombok.SneakyThrows;

public abstract class TexteReplyBotCommand extends BotCommand {

    @Autowired
    private ClientService clientService;
    @Autowired
    protected MessageSource messageSource;

    public TexteReplyBotCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] args) {
        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        prepareResponse(answer, absSender, findClient(user));
    }

    private Client findClient(User user) {
        Optional<Client> client = clientService.findById(user.getId());
        return client.orElseGet(() -> clientService.register(user));
    }

    protected abstract void prepareResponse(SendMessage answer, AbsSender absSender, Client user) throws TelegramApiException;

    @Override
    public String toString() {
        return COMMAND_INIT_CHARACTER + getCommandIdentifier() + "\n" + getDescription();
    }

}

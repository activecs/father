package com.epam.env.father.bot.commands;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.data.builder.SendMessageBuilder;
import com.epam.env.father.model.Client;
import com.epam.env.father.service.ClientService;

import lombok.SneakyThrows;

public abstract class TexteReplyBotCommand extends BotCommand {

    @Autowired
    private ClientService clientService;
    @Autowired
    protected MessageSource messageSource;
    @Autowired
    protected SendMessageBuilder sendMessageBuilder;

    public TexteReplyBotCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] args) {
        Client client = findClient(user);
        SendMessageBuilder answer = sendMessageBuilder.begin()
                .withLocale(findClient(user).getLocale())
                .withChatId(chat.getId().toString());
        sendResponse(answer, absSender, client);
    }

    private Client findClient(User user) {
        Optional<Client> client = clientService.findById(user.getId());
        return client.orElseGet(() -> clientService.register(user));
    }

    protected abstract void sendResponse(SendMessageBuilder answer, AbsSender absSender, Client user) throws TelegramApiException;

    @Override
    public String toString() {
        return COMMAND_INIT_CHARACTER + getCommandIdentifier() + "\n" + getDescription();
    }

}

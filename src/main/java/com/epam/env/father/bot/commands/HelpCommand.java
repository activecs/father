package com.epam.env.father.bot.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.model.Client;

@CommandComponent
public class HelpCommand extends TexteReplyBotCommand {

    private final ICommandRegistry commandRegistry;

    public HelpCommand(ICommandRegistry commandRegistry) {
        super("help", "Get all the commands this bot provides");
        this.commandRegistry = commandRegistry;
    }

    @Override
    protected void prepareResponse(SendMessage answer, AbsSender absSender, Client user) throws TelegramApiException {
        StringBuilder helpMessageBuilder = new StringBuilder("Help\n");
        helpMessageBuilder.append("These are the registered commands for this Bot:\n\n");
        for (BotCommand botCommand : commandRegistry.getRegisteredCommands()) {
            helpMessageBuilder.append(botCommand.toString()).append("\n\n");
        }
        answer.setText(helpMessageBuilder.toString());
        absSender.sendMessage(answer);
    }

}

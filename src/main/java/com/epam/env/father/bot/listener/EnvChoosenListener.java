package com.epam.env.father.bot.listener;

import com.epam.env.father.bot.meta.UpdateListenerComponent;
import com.epam.env.father.data.builder.ReplyKeyboardMarkupBuilder;
import com.epam.env.father.data.builder.SendMessageBuilder;
import com.epam.env.father.model.Client;
import com.epam.env.father.model.Environment;
import com.epam.env.father.repository.EnvironmentRepository;
import com.epam.env.father.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;

@Log4j2
@UpdateListenerComponent
public class EnvChoosenListener implements BotUpdateListener<String> {
    @Autowired
    private EnvironmentRepository environmentRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private SendMessageBuilder sendMessageBuilder;
    @Autowired
    private ReplyKeyboardMarkupBuilder replyKeyboardMarkupBuilder;

    @Override
    public SendMessage process(Update update, String data) {
        replyKeyboardMarkupBuilder = replyKeyboardMarkupBuilder.begin();
        List<Environment> environments = environmentRepository.findByReservedByIsNullAndCountry(data);
        environments.stream().map(Environment::getId).forEach(replyKeyboardMarkupBuilder::withFirstRowComand);
        Client client = clientService.findById(update.getCallbackQuery().getFrom().getId()).get();
        return sendMessageBuilder.begin()
                .withLocale(client.getLocale())
                .withChatId(client.getId().toString())
                .withMessage("there.are.such.envs.to.be.booked")
                .withReplyKeyboard(replyKeyboardMarkupBuilder)
                .build();
    }
}

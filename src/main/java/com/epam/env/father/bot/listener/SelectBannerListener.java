package com.epam.env.father.bot.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import com.epam.env.father.bot.meta.UpdateListenerComponent;
import com.epam.env.father.data.booking.SelectBannerData;
import com.epam.env.father.data.booking.SelectEnvironmentData;
import com.epam.env.father.data.builder.InlineKeyboardButtonBuilder;
import com.epam.env.father.data.builder.InlineKeyboardMarkupBuilder;
import com.epam.env.father.data.builder.SendMessageBuilder;
import com.epam.env.father.model.Client;
import com.epam.env.father.model.Environment;
import com.epam.env.father.repository.EnvironmentRepository;
import com.epam.env.father.service.ClientService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@UpdateListenerComponent
public class SelectBannerListener implements BotUpdateListener<SelectBannerData> {
    @Autowired
    private EnvironmentRepository environmentRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private SendMessageBuilder sendMessageBuilder;
    @Autowired
    private InlineKeyboardMarkupBuilder markupBuilder;
    @Autowired
    private InlineKeyboardButtonBuilder buttonBuilder;

    @Override
    public SendMessage process(Update update, SelectBannerData bannerData) {
        Client client = clientService.findById(update.getCallbackQuery().getFrom().getId()).get();
        InlineKeyboardMarkupBuilder inlineKeyboardMarkupBuilder = markupBuilder.begin().withLocale(client.getLocale());
        List<Environment> environments = environmentRepository.findByReservedByIsNullAndCountry(bannerData.getBanner());
        environments.stream().map(Environment::getId).forEach(env -> inlineKeyboardMarkupBuilder.withFistLineButton(createButton(env, bannerData.getBanner())));
        return sendMessageBuilder.begin()
                .withLocale(client.getLocale())
                .withChatId(client.getId().toString())
                .withMessage("there.are.such.envs.to.be.booked")
                .withReplyKeyboard(inlineKeyboardMarkupBuilder)
                .build();
    }

    private InlineKeyboardButtonBuilder createButton(String environmentCode, String bannerCode) {
        return buttonBuilder.begin()
                .withData(new SelectEnvironmentData(bannerCode, environmentCode))
                .withMessage(environmentCode);
    }

}

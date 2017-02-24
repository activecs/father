package com.epam.env.father.bot.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import com.epam.env.father.bot.meta.UpdateListenerComponent;
import com.epam.env.father.data.booking.BookEnvironmentData;
import com.epam.env.father.data.builder.SendMessageBuilder;
import com.epam.env.father.model.Client;
import com.epam.env.father.model.Environment;
import com.epam.env.father.service.ClientService;
import com.epam.env.father.service.EnvironmentReservationService;

import javaslang.control.Try;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UpdateListenerComponent
public class BookEnvironmentListener implements BotUpdateListener<BookEnvironmentData> {

    @Autowired
    private EnvironmentReservationService reservationService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private SendMessageBuilder sendMessageBuilder;

    @Override
    public SendMessage process(Update update, BookEnvironmentData environmentData) {
        Client client = clientService.findById(update.getCallbackQuery().getFrom().getId()).get();
        Try<Environment> reservationResult = Try.of(() -> reserveEnvironment(environmentData, client));
        String message = reservationResult.isSuccess()?"environment.booking.confirmation":"environment.booking.failed";
        return sendMessageBuilder.begin()
                .withLocale(client.getLocale())
                .withChatId(client.getId().toString())
                .withMessage(message)
                .withMessageArg(environmentData.getEnvironmentData().getBannerCode())
                .withMessageArg(environmentData.getEnvironmentData().getEnvinronmentCode())
                .withMessageArg(environmentData.getReservationPeriod())
                .build();
    }

    private Environment reserveEnvironment(BookEnvironmentData environmentData, Client client) {
        return reservationService.reserveEnvironment(environmentData.getEnvironmentData().getEnvinronmentCode(), client, environmentData.getReservationPeriod());
    }

}

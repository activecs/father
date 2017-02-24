package com.epam.env.father.bot.listener;

import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import com.epam.env.father.bot.meta.UpdateListenerComponent;
import com.epam.env.father.data.booking.BookEnvironmentData;
import com.epam.env.father.data.booking.SelectEnvironmentData;
import com.epam.env.father.data.builder.InlineKeyboardButtonBuilder;
import com.epam.env.father.data.builder.InlineKeyboardMarkupBuilder;
import com.epam.env.father.data.builder.SendMessageBuilder;
import com.epam.env.father.model.Client;
import com.epam.env.father.service.ClientService;
import com.epam.env.father.service.EnvironmentReservationService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@UpdateListenerComponent
public class SelectEnvironmentListener implements BotUpdateListener<SelectEnvironmentData> {
    @Autowired
    private EnvironmentReservationService reservationService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private SendMessageBuilder sendMessageBuilder;
    @Autowired
    private InlineKeyboardButtonBuilder buttonBuilder;
    @Autowired
    private InlineKeyboardMarkupBuilder markupBuilder;

    @Override
    public SendMessage process(Update update, SelectEnvironmentData environmentData) {
        Client client = clientService.findById(update.getCallbackQuery().getFrom().getId()).get();
        InlineKeyboardMarkupBuilder inlineKeyboardMarkupBuilder = markupBuilder.begin().withLocale(client.getLocale());
        reservationService.getPossibleReservationPeriods().stream()
                .forEach(period -> inlineKeyboardMarkupBuilder.withFistLineButton(createButton(environmentData, period)));
        return sendMessageBuilder.begin()
                .withLocale(client.getLocale())
                .withChatId(client.getId().toString())
                .withMessage("there.are.such.reservation.period")
                .withReplyKeyboard(inlineKeyboardMarkupBuilder)
                .build();
    }

    private InlineKeyboardButtonBuilder createButton(SelectEnvironmentData environmentData, Long period) {
        return buttonBuilder.begin()
                .withData(new BookEnvironmentData(environmentData, period))
                .withMessage("available.time.period.format")
                .withMessageArgs(asList(period));
    }

}

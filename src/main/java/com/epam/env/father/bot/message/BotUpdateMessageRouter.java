package com.epam.env.father.bot.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import com.epam.env.father.bot.message.processor.TextMessageProcessor;
import com.epam.env.father.bot.message.processor.UpdateMessageProcessor;

@Component
public class BotUpdateMessageRouter {

    @Autowired
    private UpdateMessageProcessor updateMessageProcessor;
    @Autowired
    private TextMessageProcessor textMessageProcessor;

    public SendMessage process(Update update) {
        if (update.hasCallbackQuery())
            return updateMessageProcessor.process(update)
                    .orElseGet(() -> textMessageProcessor.process(update));
        else
            return textMessageProcessor.process(update);
    }

}

package com.epam.env.father.bot.message.processor;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import com.epam.env.father.bot.listener.BotUpdateListener;
import com.epam.env.father.bot.meta.BotName;
import com.epam.env.father.bot.meta.UpdateListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import javaslang.control.Try;

@Component
public class UpdateMessageProcessor {

    @UpdateListener(BotName.ENV_FATHER)
    @Autowired
    private List<BotUpdateListener> updateListeners;
    @Autowired
    private ObjectMapper jacksonObjectMapper;


    public Optional<SendMessage> process(Update update) {
        return findMessageHandler(update).map(listener -> {
             Object requestData = convertRequestData(update, listener).get();
             return listener.process(update, requestData);
        });
    }

    private Optional<BotUpdateListener> findMessageHandler(Update update) {
        return updateListeners.stream().filter(canHandleRequest(update)).findAny();
    }

    private Predicate<BotUpdateListener> canHandleRequest(Update update) {
        return listener -> convertRequestData(update, listener).isSuccess();
    }

    private Try<Object> convertRequestData(Update update, BotUpdateListener listener) {
        final String data = update.getCallbackQuery().getData();
        final Class<?> listenerGenericType = getListenerGenericType(listener);
        return Try.of(() -> jacksonObjectMapper.readValue(data, listenerGenericType));
    }

    private Class getListenerGenericType(BotUpdateListener listener) {
        return GenericTypeResolver.resolveTypeArgument(listener.getClass(), BotUpdateListener.class);
    }
}

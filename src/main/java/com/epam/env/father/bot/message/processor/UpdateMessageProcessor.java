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
import com.epam.env.father.data.AbstractCallBackData;
import com.epam.env.father.model.Callback;
import com.epam.env.father.service.CallbackStorageService;

@Component
public class UpdateMessageProcessor {

    @UpdateListener(BotName.ENV_FATHER)
    @Autowired
    private List<BotUpdateListener> updateListeners;
    @Autowired
    private CallbackStorageService callbackStorageService;

    public Optional<SendMessage> process(Update update) {
        return findMessageHandler(update).map(listener -> {
             Optional<AbstractCallBackData> requestData = getCallbackData(update);
             return listener.process(update, requestData.get());
        });
    }

    private Optional<BotUpdateListener> findMessageHandler(Update update) {
        return updateListeners.stream().filter(canHandleRequest(update)).findAny();
    }

    private Predicate<BotUpdateListener> canHandleRequest(Update update) {
        return listener -> canHandleRequest(update, listener);
    }

    private boolean canHandleRequest(Update request, BotUpdateListener listener) {
        Class<?> listenerGenericType = getListenerGenericType(listener);
        return getCallbackData(request)
                .map(listenerGenericType::isInstance)
                .orElse(false);
    }

    private Optional<AbstractCallBackData> getCallbackData(Update update) {
        String callbackKey = update.getCallbackQuery().getData();
        return callbackStorageService.findByKey(callbackKey).map(Callback::getData);
    }

    private Class getListenerGenericType(BotUpdateListener listener) {
        return GenericTypeResolver.resolveTypeArgument(listener.getClass(), BotUpdateListener.class);
    }

}

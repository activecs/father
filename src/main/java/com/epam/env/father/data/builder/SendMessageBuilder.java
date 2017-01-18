package com.epam.env.father.data.builder;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Locale.ENGLISH;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;

@Scope(scopeName = SCOPE_PROTOTYPE)
@Component
public class SendMessageBuilder {

    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    protected MessageSource messageSource;

    private Locale locale = ENGLISH;
    private String chatId;
    private String message;
    private List<Object> messageArgs = newArrayList();
    private Optional<AbstractReplyKeyboardBuilder> keyboardBuilder = Optional.empty();

    public SendMessageBuilder begin() {
        return beanFactory.getBean(this.getClass());
    }

    public SendMessageBuilder withChatId(String chatId) {
        this.chatId = chatId;
        return this;
    }

    public SendMessageBuilder withLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public SendMessageBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public SendMessageBuilder withMessageArg(Object messageArg) {
        this.messageArgs.add(messageArg);
        return this;
    }

    public SendMessageBuilder withMessageArgs(List<Object> messageArgs) {
        this.messageArgs.addAll(messageArgs);
        return this;
    }

    public SendMessageBuilder withReplyKeyboard(AbstractReplyKeyboardBuilder keyboardBuilder) {
        this.keyboardBuilder = Optional.of(keyboardBuilder);
        return this;
    }


    public SendMessage build() {
        SendMessage sendMessage = new SendMessage();
        keyboardBuilder
                .map(b->b.withLocale(locale))
                .map(AbstractReplyKeyboardBuilder::build)
                .ifPresent(sendMessage::setReplyMarkup);
        sendMessage.setText(messageSource.getMessage(message, messageArgs.toArray(), locale));
        sendMessage.setChatId(chatId);
        return sendMessage;
    }

}

package com.epam.env.father.data.builder;

import static java.util.Locale.ENGLISH;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.Locale;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@Scope(scopeName = SCOPE_PROTOTYPE)
@Component
public class InlineKeyboardButtonBuilder {

    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private ObjectMapper jacksonObjectMapper;
    @Autowired
    protected MessageSource messageSource;

    private String message;
    private Object data;
    private Locale locale = ENGLISH;
    private Object[] messageArgs = new Object[0];

    private InlineKeyboardButtonBuilder() {
    }

    public InlineKeyboardButtonBuilder begin() {
        return beanFactory.getBean(this.getClass());
    }

    public InlineKeyboardButtonBuilder begin(Locale locale) {
        InlineKeyboardButtonBuilder builder = begin();
        builder.withLocale(locale);
        return builder;
    }

    private InlineKeyboardButtonBuilder withLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public InlineKeyboardButtonBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public InlineKeyboardButtonBuilder withMessageArgs(Object[] messageArgs) {
        this.messageArgs = messageArgs;
        return this;
    }

    public InlineKeyboardButtonBuilder withData(Object data) {
        this.data = data;
        return this;
    }

    @SneakyThrows
    public InlineKeyboardButton build() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(messageSource.getMessage(message, messageArgs, locale));
        button.setCallbackData(jacksonObjectMapper.writeValueAsString(data));
        return button;
    }

}

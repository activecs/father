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

import lombok.SneakyThrows;

//TODO:builder is unfinished, need further development
@Scope(scopeName = SCOPE_PROTOTYPE)
@Component
public class InlineKeyboardMarkupBuilder {

    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    protected MessageSource messageSource;

    private String message;
    private Object data;
    private Locale locale = ENGLISH;
    private Object[] messageArgs = new Object[0];

    public InlineKeyboardMarkupBuilder withMessageArgs(Object[] messageArgs) {
        this.messageArgs = messageArgs;
        return this;
    }

    private InlineKeyboardMarkupBuilder withLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public InlineKeyboardMarkupBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    @SneakyThrows
    public InlineKeyboardButton build() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(messageSource.getMessage(message, messageArgs, locale));
        return button;
    }

}

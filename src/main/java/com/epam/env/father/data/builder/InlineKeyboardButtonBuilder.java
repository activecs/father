package com.epam.env.father.data.builder;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Locale.ENGLISH;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.List;
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

    private Object data;
    private Locale locale = ENGLISH;
    private String message;
    private List<Object> messageArgs = newArrayList();

    private InlineKeyboardButtonBuilder() {
    }

    public InlineKeyboardButtonBuilder begin() {
        return beanFactory.getBean(this.getClass());
    }

    public InlineKeyboardButtonBuilder withLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public InlineKeyboardButtonBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public InlineKeyboardButtonBuilder withMessageArgs(List<Object> messageArgs) {
        this.messageArgs.addAll(messageArgs);
        return this;
    }

    public InlineKeyboardButtonBuilder withData(Object data) {
        this.data = data;
        return this;
    }

    @SneakyThrows
    public InlineKeyboardButton build() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(messageSource.getMessage(message, messageArgs.toArray(), locale));
        button.setCallbackData(jacksonObjectMapper.writeValueAsString(data));
        return button;
    }

}

package com.epam.env.father.data.builder;

import java.util.Locale;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

public interface AbstractReplyKeyboardBuilder {

    AbstractReplyKeyboardBuilder withLocale(Locale locale);

    ReplyKeyboard build();
}

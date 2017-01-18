package com.epam.env.father.data.builder;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.toList;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Scope(scopeName = SCOPE_PROTOTYPE)
@Component
public class InlineKeyboardMarkupBuilder implements AbstractReplyKeyboardBuilder {

    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    protected MessageSource messageSource;

    private Locale locale = ENGLISH;
    private List<InlineKeyboardButtonBuilder> firstLine = newArrayList();
    private List<InlineKeyboardButtonBuilder> secondLine = newArrayList();

    public InlineKeyboardMarkupBuilder begin() {
        return beanFactory.getBean(this.getClass());
    }

    public InlineKeyboardMarkupBuilder withLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public InlineKeyboardMarkupBuilder withFistLineButton(InlineKeyboardButtonBuilder buttonBuilder) {
        firstLine.add(buttonBuilder);
        return this;
    }

    public InlineKeyboardMarkupBuilder withSecondLineButton(InlineKeyboardButtonBuilder buttonBuilder) {
        secondLine.add(buttonBuilder);
        return this;
    }

    @Override
    public InlineKeyboardMarkup build() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(asList(buildButtons(firstLine), buildButtons(secondLine)));
        return keyboardMarkup;
    }

    private List<InlineKeyboardButton> buildButtons(List<InlineKeyboardButtonBuilder> builders) {
        return builders.stream()
                .peek(b->b.withLocale(locale))
                .map(InlineKeyboardButtonBuilder::build)
                .collect(toList());
    }

}

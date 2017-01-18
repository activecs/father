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
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

@Scope(scopeName = SCOPE_PROTOTYPE)
@Component
public class ReplyKeyboardMarkupBuilder implements AbstractReplyKeyboardBuilder {

    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    protected MessageSource messageSource;

    private Locale locale = ENGLISH;
    private KeyboardRow firstRowCommand = new KeyboardRow();
    private boolean selective;
    private boolean resizeKeyboard;

    public ReplyKeyboardMarkupBuilder begin() {
        return beanFactory.getBean(this.getClass());
    }

    public ReplyKeyboardMarkupBuilder withLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public ReplyKeyboardMarkupBuilder withFirstRowComand(String command) {
        firstRowCommand.add(command);
        return this;
    }

    public ReplyKeyboardMarkupBuilder withResizeKeyboard(boolean resizeKeyboard) {
        this.resizeKeyboard = resizeKeyboard;
        return this;
    }

    public ReplyKeyboardMarkupBuilder withSelective(boolean selective) {
        this.selective = selective;
        return this;
    }

    @Override
    public ReplyKeyboard build() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(resizeKeyboard);
        replyKeyboardMarkup.setSelective(selective);
        replyKeyboardMarkup.setKeyboard(buildCommandRows());
        return replyKeyboardMarkup;
    }

    private List<KeyboardRow> buildCommandRows() {
        List<KeyboardRow> commands = newArrayList();
        commands.add(firstRowCommand);
        return newArrayList(commands);
    }

}

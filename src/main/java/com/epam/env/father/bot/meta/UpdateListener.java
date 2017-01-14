package com.epam.env.father.bot.meta;

import static com.epam.env.father.bot.meta.BotName.ENV_FATHER;

import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier
public @interface UpdateListener {
    BotName value() default ENV_FATHER;
}

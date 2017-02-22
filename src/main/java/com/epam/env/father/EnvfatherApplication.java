package com.epam.env.father;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

import com.rits.cloning.Cloner;

@EnableScheduling
@EnableMongoRepositories
@SpringBootApplication
public class EnvfatherApplication {

	public static void main(String[] args) {
        ApiContextInitializer.init();
		SpringApplication.run(EnvfatherApplication.class, args);
	}

    @Bean
    public TelegramBotsApi botsApi() {
        return new TelegramBotsApi();
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("i18n/messages");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    public Cloner cloner() {
	    return new Cloner();
    }

}

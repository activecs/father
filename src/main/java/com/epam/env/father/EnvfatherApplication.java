package com.epam.env.father;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

@EnableMongoRepositories()
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

}

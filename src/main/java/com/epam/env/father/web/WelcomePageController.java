package com.epam.env.father.web;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomePageController {

    @Value("${bot.id:father}")
    private String applicationName;

    @GetMapping("/")
    @ResponseBody
    public String welcome() {
        return MessageFormat.format("welcome to {0} Telegram bot", applicationName);
    }

}

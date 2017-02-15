package com.epam.env.father.bot.commands;

import static java.lang.System.lineSeparator;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.epam.env.father.bot.meta.CommandComponent;
import com.epam.env.father.data.builder.SendMessageBuilder;
import com.epam.env.father.model.Client;
import com.epam.env.father.model.Environment;
import com.epam.env.father.service.EnvironmentService;

@CommandComponent
public class StatusCommand extends TexteReplyBotCommand {

	private static final String LINE_DELIMETER = lineSeparator() + "_____________________________________________"
			+ lineSeparator();

	@Autowired
	private EnvironmentService environmentService;

	public StatusCommand() {
		super("status", "With this command you can get up to date info regarding envs statuses");
	}

	@Override
	protected void sendResponse(SendMessageBuilder answer, AbsSender absSender, Client user)
			throws TelegramApiException {
		Map<String, List<Environment>> envsByCountry = environmentService.getAllAvailable().stream()
				.collect(Collectors.groupingBy(Environment::getCountry));
		String formattedMessage = new String();
		for (Entry<String, List<Environment>> entry : envsByCountry.entrySet()) {
			formattedMessage += LINE_DELIMETER + entry.getKey() + LINE_DELIMETER + formatEnvText(entry.getValue());
		}
		answer.withMessage(formattedMessage);
		absSender.sendMessage(answer.build());
	}

	private String formatEnvText(List<Environment> envs) {
		return envs.stream().map(Environment::toString).collect(Collectors.joining(lineSeparator()));
	}

}

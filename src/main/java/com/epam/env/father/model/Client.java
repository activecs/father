package com.epam.env.father.model;

import java.util.Locale;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@EqualsAndHashCode(of = "id")
public class Client {

    @Id
    @Getter
    public Integer id;
    @Getter
    @Setter
    private Long chatId;
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private Locale locale = Locale.ENGLISH;
    @Getter
    @Setter
    private ClientNotificationStatus notificationStatus;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}


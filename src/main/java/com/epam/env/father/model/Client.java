package com.epam.env.father.model;

import java.util.Locale;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude="chatId")
public class Client {

    @Id
    public Integer id;
    @Getter
    @Setter
    private String chatId;
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

}


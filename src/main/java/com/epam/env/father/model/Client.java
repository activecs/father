package com.epam.env.father.model;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(of = "id")
@ToString(exclude="chatId")
@Builder
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

}


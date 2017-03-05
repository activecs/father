package com.epam.env.father.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.epam.env.father.data.AbstractCallBackData;

import lombok.Getter;


public class Callback {

    @Indexed(expireAfterSeconds = 604800)
    private LocalDateTime createdDateTime;
    @Id
    @Getter
    private String key;
    @Getter
    private AbstractCallBackData data;

    public Callback(AbstractCallBackData data) {
        this.data = data;
    }
}

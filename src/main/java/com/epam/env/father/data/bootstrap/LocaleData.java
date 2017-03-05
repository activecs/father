package com.epam.env.father.data.bootstrap;

import com.epam.env.father.data.AbstractCallBackData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class LocaleData implements AbstractCallBackData {

    @Getter
    @Setter
    private String language;


}

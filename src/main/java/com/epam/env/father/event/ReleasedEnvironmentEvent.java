package com.epam.env.father.event;

import com.epam.env.father.model.Environment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ReleasedEnvironmentEvent {

    @Getter
    @Setter
    private Environment environment;

}

package com.epam.env.father.data.booking;


import com.epam.env.father.data.AbstractCallBackData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class BookEnvironmentData implements AbstractCallBackData {

    @Getter
    @Setter
    private SelectEnvironmentData environmentData;
    @Getter
    @Setter
    private Long reservationPeriod;

}

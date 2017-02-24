package com.epam.env.father.data.booking;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class BookEnvironmentData {

    @Getter
    @Setter
    private SelectEnvironmentData environmentData;
    @Getter
    @Setter
    private Long reservationPeriod;

}

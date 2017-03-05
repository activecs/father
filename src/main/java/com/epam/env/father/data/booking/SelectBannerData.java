package com.epam.env.father.data.booking;

import com.epam.env.father.data.AbstractCallBackData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class SelectBannerData implements AbstractCallBackData {

    @Getter
    @Setter
    private String banner;

}

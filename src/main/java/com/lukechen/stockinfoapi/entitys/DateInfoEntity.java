package com.lukechen.stockinfoapi.entitys;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateInfoEntity {

    String date;
    Float open;
    Float close;
    Float high;
    Float low;
    Integer volume;
}

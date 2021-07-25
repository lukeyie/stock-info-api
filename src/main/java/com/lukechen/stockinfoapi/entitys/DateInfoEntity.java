package com.lukechen.stockinfoapi.entitys;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateInfoEntity {

    String date;
    Double open;
    Double close;
    Double high;
    Double low;
    Integer volume;
}

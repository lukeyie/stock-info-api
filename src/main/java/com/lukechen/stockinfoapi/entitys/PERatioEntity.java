package com.lukechen.stockinfoapi.entitys;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
public class PERatioEntity {

    String stockName;
    String ticker;
    LinkedHashMap<Integer, Float> peRatios;
}

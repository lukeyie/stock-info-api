package com.lukechen.stockinfoapi.entitys;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class StockPERatioEntity {

    String stockName;
    String ticker;
    HashMap<Integer, Double> peRatios;
}

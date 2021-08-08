package com.lukechen.stockinfoapi.entitys;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
public class PERatioEntity {

    String stockName;
    String ticker;
    LinkedList<YearPERatioPairEntity> peRatios;
}

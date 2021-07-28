package com.lukechen.stockinfoapi.entitys;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class IncomeStatementEntity {

    int year;
    int season;
    int revenue;
    int cost;
    @Field("gp")
    int grossProfit;
    @Field("oe")
    int operatingExpenses;
    @Field("oi")
    int operatingIncome;
    @Field("nie")
    int nonoperatingAmount;
    @Field("btax")
    int incomeBeforeTax;
    @Field("ni")
    int netIncome;
    float eps;
}

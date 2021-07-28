package com.lukechen.stockinfoapi.entitys;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Collection;

@Getter
@Setter
@Document(collection = "stocks")
public class StockEntity {

    @MongoId
    ObjectId id;
    @Field("stock_name")
    String stockName;
    String ticker;
    @Field("date_info")
    Collection<DateInfoEntity> dateInfo;
    @Field("income_statements")
    Collection<IncomeStatementEntity> incomeStatements;
}



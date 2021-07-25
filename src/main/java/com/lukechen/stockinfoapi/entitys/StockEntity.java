package com.lukechen.stockinfoapi.entitys;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

@Getter
@Setter
@Document(collection = "price_volume")
public class StockEntity {

    @MongoId
    ObjectId id;
    @Field("stock_name")
    String stockName;
    String ticker;
    @Field("date_info")
    Collection<DateInfoEntity> dateInfo;
}



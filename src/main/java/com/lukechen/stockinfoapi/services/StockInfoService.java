package com.lukechen.stockinfoapi.services;

import com.lukechen.stockinfoapi.dao.StockDAO;
import com.lukechen.stockinfoapi.entitys.DateInfoEntity;
import com.lukechen.stockinfoapi.entitys.StockEntity;
import com.lukechen.stockinfoapi.entitys.StockPERatioEntity;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class StockInfoService {

    @Autowired
    private StockDAO stockDAO;

    public StockEntity getStockInfoEntityByTicker(String ticker) {
        return findByTicker(ticker);
    }

    public  StockPERatioEntity getStockPERatioEntity(String ticker) throws ParseException {
        StockPERatioEntity stockPERatioEntity = new StockPERatioEntity();
        StockEntity stockEntity = findByTicker(ticker);
        if(stockEntity == null){
            return null;
        }

        stockPERatioEntity.setStockName(stockEntity.getStockName());
        stockPERatioEntity.setTicker(stockEntity.getTicker());
        HashMap<Integer, Double> highestClose = getYearHighestClose(stockEntity.getDateInfo());
        stockPERatioEntity.setPeRatios(getPERatios(highestClose));


        return stockPERatioEntity;
    }

    private StockEntity findByTicker(String ticker) {
            Optional<StockEntity> stockEntity = stockDAO.findOneByTicker(ticker);

        return stockEntity.orElse(null);
    }

    private HashMap<Integer, Double> getYearHighestClose(
            Collection<DateInfoEntity> dateInfoEntity) throws ParseException {

        HashMap<Integer, Double> highestClose = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        for(DateInfoEntity dateInfo : dateInfoEntity){
            if(dateInfo.getClose().isNaN()){
                continue;
            }

            Date date = formatter.parse(dateInfo.getDate());
            calendar.setTime(date);
            Integer year = calendar.get(Calendar.YEAR);
            if(highestClose.containsKey(year)){
                highestClose.replace(year, Math.max(highestClose.get(year), dateInfo.getClose()));
            }
            else{
                highestClose.put(year, dateInfo.getClose());
            }
        }

        return highestClose;
    }

    private HashMap<Integer, Double> getPERatios(HashMap<Integer, Double> highestClose){
        return highestClose;
    }
}

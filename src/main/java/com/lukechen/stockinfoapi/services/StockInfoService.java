package com.lukechen.stockinfoapi.services;

import com.lukechen.stockinfoapi.dao.StockDAO;
import com.lukechen.stockinfoapi.entitys.DateInfoEntity;
import com.lukechen.stockinfoapi.entitys.IncomeStatementEntity;
import com.lukechen.stockinfoapi.entitys.PERatioEntity;
import com.lukechen.stockinfoapi.entitys.StockEntity;
import com.lukechen.stockinfoapi.entitys.YearPERatioPairEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StockInfoService {

    @Autowired
    private StockDAO stockDAO;

    public StockEntity getStockInfoEntityByTicker(String ticker) {
        return findByTicker(ticker);
    }

    public PERatioEntity getPERatioEntity(String ticker) throws ParseException {
        PERatioEntity peRatioEntity = new PERatioEntity();
        StockEntity stockEntity = findByTicker(ticker);
        if (stockEntity == null) {
            return null;
        }
        peRatioEntity.setStockName(stockEntity.getStockName());
        peRatioEntity.setTicker(stockEntity.getTicker());
        peRatioEntity.setPeRatios(new LinkedList<>());

        HashMap<Integer, Float> highestCloses = getYearHighestClose(stockEntity.getDateInfo());
        HashMap<Integer, ArrayList<IncomeStatementEntity>> epsSortedISList =
                getEPSSortedIncomeStatements(stockEntity.getIncomeStatements());

        LinkedList<YearPERatioPairEntity> sortedPERatios = new LinkedList<>();
        for (Map.Entry<Integer, ArrayList<IncomeStatementEntity>> pair : epsSortedISList
                .entrySet()) {
            Float highestClose = highestCloses.get(pair.getKey());
            float eps = pair.getValue().get(pair.getValue().size() - 1).getEps();
            float peRatio = highestClose / (eps * 4);

            YearPERatioPairEntity yearPERatioPairEntity = new YearPERatioPairEntity();
            yearPERatioPairEntity.setYear(pair.getKey());
            yearPERatioPairEntity.setPeRatio(peRatio);
            sortedPERatios.add(yearPERatioPairEntity);
        }

        sortedPERatios.sort((a, b) -> a.getYear().compareTo(b.getYear()));
        peRatioEntity.setPeRatios(sortedPERatios);

        return peRatioEntity;
    }

    private StockEntity findByTicker(String ticker) {
        Optional<StockEntity> stockEntity = stockDAO.findOneByTicker(ticker);

        return stockEntity.orElse(null);
    }

    private HashMap<Integer, Float> getYearHighestClose(Collection<DateInfoEntity> dateInfoEntity)
            throws ParseException {

        HashMap<Integer, Float> highestClose = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        for (DateInfoEntity dateInfo : dateInfoEntity) {
            if (dateInfo.getClose().isNaN()) {
                continue;
            }

            Date date = formatter.parse(dateInfo.getDate());
            calendar.setTime(date);
            Integer year = calendar.get(Calendar.YEAR);
            if (highestClose.containsKey(year)) {
                highestClose.replace(year, Math.max(highestClose.get(year), dateInfo.getClose()));
            } else {
                highestClose.put(year, dateInfo.getClose());
            }
        }

        return highestClose;
    }

    private HashMap<Integer, ArrayList<IncomeStatementEntity>> getEPSSortedIncomeStatements(
            Collection<IncomeStatementEntity> incomeStatementEntities) {
        HashMap<Integer, ArrayList<IncomeStatementEntity>> epsSortedISList = new HashMap<>();
        for (IncomeStatementEntity dto : incomeStatementEntities) {
            if (epsSortedISList.containsKey(dto.getYear())) {
                epsSortedISList.get(dto.getYear()).add(dto);
            } else {
                epsSortedISList.put(dto.getYear(), new ArrayList<>());
                epsSortedISList.get(dto.getYear()).add(dto);
            }
        }

        Iterator<Map.Entry<Integer, ArrayList<IncomeStatementEntity>>> iterator =
                epsSortedISList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, ArrayList<IncomeStatementEntity>> pair = iterator.next();
            if (pair.getValue().size() != 4) {
                iterator.remove();
                continue;
            }
            pair.getValue().sort(Comparator
                    .<IncomeStatementEntity, Float>comparing(IncomeStatementEntity::getEps));
        }

        return epsSortedISList;
    }
}

package com.lukechen.stockinfoapi.dao;

import com.lukechen.stockinfoapi.entitys.StockEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StockDAO extends MongoRepository<StockEntity, String> {

    public Optional<StockEntity> findOneByTicker(String ticker);
}

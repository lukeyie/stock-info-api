package com.lukechen.stockinfoapi.controllers;

import com.lukechen.stockinfoapi.entitys.StockEntity;
import com.lukechen.stockinfoapi.entitys.StockPERatioEntity;
import com.lukechen.stockinfoapi.services.StockInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/stock")
public class StockInfoController {

    @Autowired
    StockInfoService stockInfoService;

    @GetMapping("/{ticker}")
    public ResponseEntity getStockInfo(@PathVariable String ticker){
        StockEntity stockEntity = stockInfoService.getStockInfoEntityByTicker(ticker);

        return ResponseEntity.status(HttpStatus.OK).body(stockEntity);
    }

    @GetMapping("/peratio/{ticker}")
    public ResponseEntity getStockPERatio(@PathVariable String ticker) throws ParseException {
        StockPERatioEntity stockPERatioEntity = stockInfoService.getStockPERatioEntity(ticker);
        return ResponseEntity.status(HttpStatus.OK).body(stockPERatioEntity);
    }
}

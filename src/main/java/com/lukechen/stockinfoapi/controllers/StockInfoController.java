package com.lukechen.stockinfoapi.controllers;

import com.lukechen.stockinfoapi.entitys.PERatioEntity;
import com.lukechen.stockinfoapi.entitys.StockEntity;
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

    @GetMapping("/date-info/{ticker}")
    public ResponseEntity<StockEntity> getStockDateInfo(@PathVariable String ticker) {
        StockEntity stockEntity = stockInfoService.getStockInfoEntityByTicker(ticker);

        return ResponseEntity.status(HttpStatus.OK).body(stockEntity);
    }

    @GetMapping("/pe-ratios/{ticker}")
    public ResponseEntity<PERatioEntity> getStockPERatios(@PathVariable String ticker)
            throws ParseException {
        PERatioEntity peRatioEntity = stockInfoService.getPERatioEntity(ticker);

        return ResponseEntity.status(HttpStatus.OK).body(peRatioEntity);
    }
}

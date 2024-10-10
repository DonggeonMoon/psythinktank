package com.dgmoonlabs.psythinktank.domain.stock.controller;

import com.dgmoonlabs.psythinktank.domain.stock.dto.StockSearchRequest;
import com.dgmoonlabs.psythinktank.domain.stock.dto.StockSearchResponse;
import com.dgmoonlabs.psythinktank.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks")
public class StockRestController {
    private final StockService stockService;

    @PostMapping("/search/symbol")
    public ResponseEntity<StockSearchResponse> searchBySymbol(
            @Valid
            @RequestBody
            StockSearchRequest stockSearchRequest) {
        return ResponseEntity.ok(
                stockService.getStocksBySymbol(stockSearchRequest)
        );
    }

    @PostMapping("/search/stockName")
    public ResponseEntity<StockSearchResponse> searchByName(
            @Valid
            @RequestBody
            StockSearchRequest stockSearchRequest) {
        return ResponseEntity.ok(
                stockService.getStocksByName(stockSearchRequest)
        );
    }
}

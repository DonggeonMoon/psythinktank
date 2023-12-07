package com.dgmoonlabs.psythinktank.domain.stock.controller;

import com.dgmoonlabs.psythinktank.domain.stock.dto.StockSearchRequest;
import com.dgmoonlabs.psythinktank.domain.stock.dto.StockSearchResponse;
import com.dgmoonlabs.psythinktank.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockRestController {
    private final StockService stockService;

    @PostMapping("/searchBySymbol")
    public ResponseEntity<StockSearchResponse> searchBySymbol(@RequestBody StockSearchRequest stockSearchRequest) {
        return ResponseEntity.ok(stockService.selectStocksBySymbol(stockSearchRequest));
    }

    @PostMapping("/searchByStockName")
    public ResponseEntity<StockSearchResponse> searchByName(@RequestBody StockSearchRequest stockSearchRequest) {
        return ResponseEntity.ok(stockService.selectStocksByName(stockSearchRequest));
    }
}

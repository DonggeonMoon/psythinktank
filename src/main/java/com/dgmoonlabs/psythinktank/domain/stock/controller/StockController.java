package com.dgmoonlabs.psythinktank.domain.stock.controller;

import com.dgmoonlabs.psythinktank.domain.stock.dto.StockSearchRequest;
import com.dgmoonlabs.psythinktank.domain.stock.dto.StockSearchResponse;
import com.dgmoonlabs.psythinktank.domain.stock.model.StockInfo;
import com.dgmoonlabs.psythinktank.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.STOCK;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.STOCK_LIST;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/stockList")
    public String getStocks(Pageable pageable, Model model) {
        Page<StockInfo> stocks = stockService.selectStocks(pageable);
        model.addAttribute(STOCKS_KEY.getText(), stocks);
        return STOCK_LIST.getText();
    }

    @PostMapping("/searchBySymbol")
    @ResponseBody
    public ResponseEntity<StockSearchResponse> searchBySymbol(@RequestBody StockSearchRequest stockSearchRequest) {
        return ResponseEntity.ok(stockService.selectStocksBySymbol(stockSearchRequest));
    }

    @PostMapping("/searchByStockName")
    @ResponseBody
    public ResponseEntity<StockSearchResponse> searchByName(@RequestBody StockSearchRequest stockSearchRequest) {
        return ResponseEntity.ok(stockService.selectStocksByName(stockSearchRequest));
    }

    @GetMapping("/stock")
    public String getStock(String symbol, Model model) {
        model.addAttribute(STOCK_KEY.getText(), stockService.selectStock(symbol));
        model.addAttribute(HRR_KEY.getText(), stockService.calculateGrowthPotential(symbol));
        model.addAttribute(SHARE_KEY.getText(), stockService.selectSharesBySymbol(symbol));
        model.addAttribute(DIVIDEND_KEY.getText(), stockService.selectDividendBySymbol(symbol));
        model.addAttribute(GOVERNANCE_KEY.getText(), stockService.calculateGovernance(symbol));
        model.addAttribute(CORPORATE_BOARD_STABILITY.getText(), stockService.calculateBoardStability(symbol));
        return STOCK.getText();
    }
}

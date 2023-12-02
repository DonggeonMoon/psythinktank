package com.dgmoonlabs.psythinktank.domain.stock.controller;

import com.dgmoonlabs.psythinktank.domain.stock.dto.StockSearchRequest;
import com.dgmoonlabs.psythinktank.domain.stock.model.StockInfo;
import com.dgmoonlabs.psythinktank.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> searchBySymbol(@RequestBody StockSearchRequest stockSearchRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put(AJAX_RESPONSE_KEY.getText(), stockService.selectStocksBySymbol(stockSearchRequest));
        return map;
    }

    @PostMapping("/searchByStockName")
    @ResponseBody
    public Map<String, List<StockInfo>> searchByName(@RequestBody StockSearchRequest searchText) {
        Map<String, List<StockInfo>> map = new HashMap<>();
        map.put(AJAX_RESPONSE_KEY.getText(), stockService.selectStocksByName(searchText));
        return map;
    }

    @GetMapping("/stock")
    public String getStock(String symbol, Model model) {
        model.addAttribute(STOCK_KEY.getText(), stockService.selectStocksBySymbol(symbol));
        model.addAttribute(HRR_KEY.getText(), stockService.calculateGrowthPotential(symbol));
        model.addAttribute(SHARE_KEY.getText(), stockService.selectSharesBySymbol(symbol));
        model.addAttribute(DIVIDEND_KEY.getText(), stockService.selectDividendBySymbol(symbol));
        model.addAttribute(GOVERNANCE_KEY.getText(), stockService.calculateGovernance(symbol));
        model.addAttribute(CORPORATE_BOARD_STABILITY.getText(), stockService.calculateBoardStability(symbol));
        return STOCK.getText();
    }
}

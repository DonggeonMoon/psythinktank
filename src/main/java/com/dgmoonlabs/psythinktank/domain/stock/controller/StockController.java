package com.dgmoonlabs.psythinktank.domain.stock.controller;

import com.dgmoonlabs.psythinktank.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.STOCK;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.STOCK_LIST;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/stockList")
    public String getStocks(Pageable pageable, Model model) {
        model.addAttribute(STOCKS_KEY.getText(), stockService.selectStocks(pageable));
        return STOCK_LIST.getText();
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

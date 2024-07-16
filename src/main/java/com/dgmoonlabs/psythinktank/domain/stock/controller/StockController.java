package com.dgmoonlabs.psythinktank.domain.stock.controller;

import com.dgmoonlabs.psythinktank.domain.stock.service.ShareholderService;
import com.dgmoonlabs.psythinktank.domain.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.STOCK;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.STOCK_LIST;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;
    private final ShareholderService shareholderService;

    @GetMapping
    public String getStocks(Pageable pageable, Model model) {
        model.addAttribute(STOCKS_KEY.getText(), stockService.selectStocks(pageable));
        return STOCK_LIST.getText();
    }

    @GetMapping("/{symbol}")
    public String getStock(@PathVariable String symbol, Model model) {
        model.addAttribute(STOCK_KEY.getText(), stockService.selectStock(symbol));
        model.addAttribute(HRR_KEY.getText(), stockService.calculateGrowthPotential(symbol));
        model.addAttribute(SHARE_KEY.getText(), stockService.selectSharesBySymbol(symbol));
        model.addAttribute(CHART_DATASET_KEY.getText(), stockService.selectDataBySymbol(symbol));
        model.addAttribute(DIVIDEND_KEY.getText(), stockService.selectDividendBySymbol(symbol));
        model.addAttribute(GOVERNANCE_KEY.getText(), stockService.calculateGovernance(symbol));
        model.addAttribute(CORPORATE_BOARD_STABILITY.getText(), stockService.calculateBoardStability(symbol));
        model.addAttribute(STOCK_HYPE_INDEX.getText(), shareholderService.calculateStockHypeIndex(symbol));
        return STOCK.getText();
    }
}

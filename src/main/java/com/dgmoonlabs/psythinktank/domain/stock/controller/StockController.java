package com.dgmoonlabs.psythinktank.domain.stock.controller;

import com.dgmoonlabs.psythinktank.domain.stock.model.Share;
import com.dgmoonlabs.psythinktank.domain.stock.model.StockInfo;
import com.dgmoonlabs.psythinktank.domain.stock.service.StockService;
import com.dgmoonlabs.psythinktank.global.constant.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.STOCK;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/stockList")
    public String getStocks(Pageable pageable, Model model) {
        Page<StockInfo> stocks = stockService.selectAllStockInfo(pageable.getPageNumber());
        model.addAttribute(STOCKS_KEY.getText(), stocks);

        return STOCK.getText();
    }

    @PostMapping("/searchBySymbol")
    @ResponseBody
    public Map<String, Object> searchBySymbol(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put(AJAX_RESPONSE_KEY.getText(), stockService.selectAllStockInfoBySymbol(searchText));
        return map;
    }

    @PostMapping("/searchByStockName")
    @ResponseBody
    public Map<String, List<StockInfo>> searchByName(@RequestBody String searchText) {
        Map<String, List<StockInfo>> map = new HashMap<>();
        map.put(AJAX_RESPONSE_KEY.getText(), stockService.selectAllStockInfoByName(searchText));
        return map;
    }

    @GetMapping("/stock")
    public String getStock(String symbol, Model model) throws Exception {
        List<Share> shares = stockService.selectAllShareBySymbol(symbol);
        model.addAttribute(STOCK_KEY.getText(), stockService.selectOneStockInfoBySymbol(symbol));
        model.addAttribute(HRR_KEY.getText(), calculateGrowthPotential(symbol));
        model.addAttribute(SHARE_KEY.getText(), shares);
        model.addAttribute(DIVIDEND_KEY.getText(), stockService.selectOneDividendBySymbol(symbol));
        model.addAttribute(GOVERNANCE_KEY.getText(), calculateGovernance(shares));
        model.addAttribute(CORPORATE_BOARD_STABILITY.getText(), calculateBoardStability(symbol));
        return STOCK.getText();
    }

    private String calculateBoardStability(final String symbol) throws Exception {
        Double boardStability = stockService.selectOneCorporateBoardStabilityBySymbol(symbol)
                .getBoardStability();
        return Rating.evaluateBoardStability(boardStability);
    }

    private String calculateGrowthPotential(String symbol) {
        Double hrr = stockService.selectOneHRRBySymbol(symbol).getValue();
        return Rating.evaluateGrowthPotential(hrr);
    }

    private String calculateGovernance(final List<Share> shares) {
        Double currentShare = shares
                .stream()
                .max(Comparator.comparing(Share::getDate))
                .map(Share::getValue)
                .orElseThrow(NoSuchElementException::new);
        return Rating.evaluateGovernance(currentShare);
    }
}


package com.dgmoonlabs.psythinktank.domain.stock.controller;

import com.dgmoonlabs.psythinktank.domain.stock.model.Share;
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

import java.util.*;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/stockList")
    public String stockList(Pageable pageable, Model model) {
        Page<StockInfo> stocks = stockService.selectAllStockInfo(pageable.getPageNumber());
        model.addAttribute("stocks", stocks);

        return "stockList";
    }

    @PostMapping("/searchByStockCode")
    @ResponseBody
    public Map<String, Object> searchByStockCode(@RequestBody String searchText) {
        Map<String, Object> map = new HashMap<>();
        map.put("result", stockService.selectAllStockInfoByStockCode(searchText));
        return map;
    }

    @PostMapping("/searchByStockName")
    @ResponseBody
    public Map<String, List<StockInfo>> searchByStockName(@RequestBody String searchText) {
        Map<String, List<StockInfo>> map = new HashMap<>();
        map.put("result", stockService.selectAllStockInfoByStockName(searchText));
        return map;
    }

    @GetMapping("/stock")
    public String viewStock(String stockCode, Model model) throws Exception {

        List<Share> shares = stockService.selectAllShareByStockCode(stockCode);
        model.addAttribute("stock", stockService.selectOneStockInfoByStockCode(stockCode));
        model.addAttribute("hrr", calculateGrowthPotential(stockCode));
        model.addAttribute("share", shares);
        model.addAttribute("dividend", stockService.selectOneDividendByStockCode(stockCode));
        model.addAttribute("governance", calculateGovernance(shares));
        model.addAttribute("corporateBoardStability", calculateBoardStability(stockCode));
        return "viewStock";
    }

    private String calculateBoardStability(final String stockCode) throws Exception {
        Double boardStability = stockService.selectOneCorporateBoardStabilityByStockCode(stockCode)
                .getBoardStability();

        if (boardStability >= 14) {
            return "A";
        }
        if (boardStability >= 9) {
            return "B";
        }
        if (boardStability >= 4) {
            return "C";
        }
        return "D";
    }

    private String calculateGrowthPotential(String stockCode) {
        Double hrr = stockService.selectOneHRRByStockCode(stockCode).getHrr();
        if (hrr >= 10) {
            return "A";
        }
        if (hrr >= 5) {
            return "B";
        }
        if (hrr >= 0) {
            return "C";
        }
        return "D";
    }

    private String calculateGovernance(final List<Share> shares) {
        Double currentShare = shares
                .stream()
                .max(Comparator.comparing(Share::getDate))
                .map(Share::getShare)
                .orElseThrow(NoSuchElementException::new);
        if (currentShare >= 10) {
            return "A";
        }
        if (currentShare >= 5) {
            return "B";
        }
        if (currentShare >= 0) {
            return "C";
        }
        return "D";
    }
}


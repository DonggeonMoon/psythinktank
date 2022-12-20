package com.mdg.PSYThinktank.stock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdg.PSYThinktank.stock.model.StockInfo;
import com.mdg.PSYThinktank.stock.service.StockService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/stockList")
    public String stockList(@RequestParam(defaultValue = "1") int page, Model model) {
        Page<StockInfo> stockPage = stockService.selectAllStockInfo(page - 1);
        model.addAttribute("stockList", stockPage);
        model.addAttribute("currentBlock", stockPage.getNumber() / stockPage.getSize());
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
        model.addAttribute("stock", stockService.selectOneStockInfoByStockCode(stockCode));
        model.addAttribute("share", stockService.selectAllShareByStockCode(stockCode));
        model.addAttribute("hrr", stockService.selectOneHRRByStockCode(stockCode));
        model.addAttribute("dividend", stockService.selectOneDividendByStockCode(stockCode));
        model.addAttribute("corporateBoardStability", stockService.selectOneCorporateBoardStabilityByStockCode(stockCode));
        return "viewStock";
    }
}

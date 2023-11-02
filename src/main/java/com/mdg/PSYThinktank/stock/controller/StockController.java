package com.mdg.PSYThinktank.stock.controller;

import com.mdg.PSYThinktank.stock.model.StockInfo;
import com.mdg.PSYThinktank.stock.service.StockService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/stockList")
    public String stockList(Pageable pageable, Model model) {
        Page<StockInfo> stocks = stockService.selectAllStockInfo(pageable.getPageNumber());

        int startNumber = (pageable.getPageNumber() / pageable.getPageSize()) * pageable.getPageSize() + 1;
        int endNumber =
                (pageable.getPageNumber() / pageable.getPageSize()) * pageable.getPageSize() + pageable.getPageSize();

        List<Pageable> pages = IntStream.rangeClosed(startNumber, endNumber)
                .mapToObj(i -> stocks.getPageable().withPage(i))
                .collect(Collectors.toList());

        model.addAttribute("stockList", stocks);
        model.addAttribute("currentBlock", stocks.getNumber() / stocks.getSize());
        model.addAttribute("pages", pages);
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

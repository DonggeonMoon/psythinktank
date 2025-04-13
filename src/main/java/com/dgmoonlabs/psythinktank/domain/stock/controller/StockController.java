package com.dgmoonlabs.psythinktank.domain.stock.controller;

import com.dgmoonlabs.psythinktank.domain.stock.service.StockService;
import com.dgmoonlabs.psythinktank.global.limiter.DownloadLimiter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.STOCK;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.STOCK_LIST;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;
    private final DownloadLimiter downloadLimiter;

    @GetMapping
    public String getStocks(Pageable pageable, Model model) {
        model.addAttribute(STOCKS_KEY.getText(), stockService.getStocks(pageable));
        return STOCK_LIST.getText();
    }

    @PostMapping("/download/excel")
    public void downloadExcel(HttpServletResponse response) {
        if (!downloadLimiter.tryAcquire()) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return;
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");

        try (Workbook workbook = stockService.createExcel()) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            byte[] fileBytes = out.toByteArray();
            response.setContentLength(fileBytes.length);
            response.getOutputStream().write(fileBytes);
            response.getOutputStream().flush();
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } finally {
            downloadLimiter.release();
        }
    }

    @GetMapping("/{symbol}")
    public String getStock(@PathVariable String symbol, Model model) {
        model.addAttribute(STOCK_KEY.getText(), stockService.getStock(symbol));
        model.addAttribute(HRR_KEY.getText(), stockService.calculateGrowthPotential(symbol));
        model.addAttribute(SHARE_KEY.getText(), stockService.getSharesBySymbol(symbol));
        model.addAttribute(CHART_DATASET_KEY.getText(), stockService.getDataBySymbol(symbol));
        model.addAttribute(DIVIDEND_KEY.getText(), stockService.getDividendBySymbol(symbol));
        model.addAttribute(GOVERNANCE_KEY.getText(), stockService.calculateGovernance(symbol));
        model.addAttribute(CORPORATE_BOARD_STABILITY.getText(), stockService.calculateBoardStability(symbol));
        model.addAttribute(STOCK_HYPE_INDEX.getText(), stockService.calculateStockHypeIndex(symbol));
        return STOCK.getText();
    }
}

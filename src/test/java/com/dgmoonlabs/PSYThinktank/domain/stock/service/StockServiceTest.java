package com.dgmoonlabs.PSYThinktank.domain.stock.service;

import com.dgmoonlabs.psythinktank.domain.stock.dto.*;
import com.dgmoonlabs.psythinktank.domain.stock.model.Dividend;
import com.dgmoonlabs.psythinktank.domain.stock.model.Share;
import com.dgmoonlabs.psythinktank.domain.stock.model.StockInfo;
import com.dgmoonlabs.psythinktank.domain.stock.repository.StockInfoRepository;
import com.dgmoonlabs.psythinktank.domain.stock.service.StockService;
import com.dgmoonlabs.psythinktank.domain.stock.vo.ChartData;
import com.dgmoonlabs.psythinktank.domain.stock.vo.ChartDataset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {
    public static final Share SHARE = Share.builder().build();
    public static final Dividend DIVIDEND = Dividend.builder().build();
    private static final PageRequest PAGE_REQUEST = PageRequest.of(1, 10);
    private static final StockInfo STOCK = StockInfo.builder()
            .symbol("005930")
            .name("삼성전자")
            .build();
    @Mock
    private StockInfoRepository stockInfoRepository;

    @InjectMocks
    private StockService stockService;

    @Test
    void selectStocks() {
        assertThat(stockService.selectStocks(PAGE_REQUEST))
                .isEqualTo(PAGE_REQUEST);

    }

    @Test
    void selectStock() {
        assertThat(stockService.selectStock(anyString()))
                .isEqualTo(StockResponse.from(STOCK));
    }

    @Test
    void selectStocksBySymbol() {
        assertThat(stockService.selectStocksBySymbol(new StockSearchRequest("005930")))
                .isEqualTo(StockSearchResponse.from(List.of(StockResponse.from(STOCK))));
    }

    @Test
    void selectStocksByName() {
        assertThat(stockService.selectStocksByName(new StockSearchRequest("삼성전자")))
                .isEqualTo(StockSearchResponse.from(List.of(StockResponse.from(STOCK))));
    }

    @Test
    void selectSharesBySymbol() {
        assertThat(stockService.selectSharesBySymbol("005930"))
                .isEqualTo(ShareSearchResponse.from(List.of(ShareResponse.from(SHARE))));
    }

    @Test
    void selectDividendBySymbol() {
        assertThat(stockService.selectDividendBySymbol("005930"))
                .isEqualTo(DividendResponse.from(DIVIDEND));
    }

    @Test
    void calculateBoardStability() {
        assertThat(stockService.calculateBoardStability("005930"))
                .isEqualTo("");
    }

    @Test
    void calculateGrowthPotential() {
        assertThat(stockService.calculateGrowthPotential("005930"))
                .isEqualTo("");
    }

    @Test
    void calculateGovernance() {
        assertThat(stockService.calculateGovernance("005930"))
                .isEqualTo("");
    }

    @Test
    void selectDataBySymbol() {
        assertThat(stockService.selectDataBySymbol("005930"))
                .isEqualTo(new ChartData(List.of(""), List.of(new ChartDataset("", List.of(0.0)))));
    }
}
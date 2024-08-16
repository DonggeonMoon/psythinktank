package com.dgmoonlabs.psythinktank.domain.stock.service;

import com.dgmoonlabs.psythinktank.domain.stock.dto.*;
import com.dgmoonlabs.psythinktank.domain.stock.model.*;
import com.dgmoonlabs.psythinktank.domain.stock.repository.*;
import com.dgmoonlabs.psythinktank.domain.stock.vo.ChartData;
import com.dgmoonlabs.psythinktank.domain.stock.vo.ChartDataset;
import com.dgmoonlabs.psythinktank.global.constant.Rating;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {
    public static final String SYMBOL = "005930";
    public static final String NAME = "삼성전자";
    public static final CorporateBoardStability CORPORATE_BOARD_STABILITY = CorporateBoardStability.builder()
            .id(1L)
            .symbol(SYMBOL)
            .stockName(NAME)
            .value(15.0)
            .build();
    public static final Dividend DIVIDEND = Dividend.builder()
            .symbol(SYMBOL)
            .stockName(NAME)
            .value(15)
            .build();
    public static final Hrr HRR = Hrr.builder()
            .id(1L)
            .value(15.0)
            .build();
    public static final Share SHARE_1 = Share.builder()
            .id(1L)
            .date(LocalDate.of(2023, 3, 31))
            .value(45.0)
            .holderName("주주1")
            .build();
    public static final Share SHARE_2 = Share.builder()
            .id(2L)
            .date(LocalDate.of(2023, 6, 30))
            .value(45.0)
            .holderName("주주2")
            .build();
    public static final ChartData CHART_DATA = new ChartData(List.of("2023-03-31", "2023-06-30"), List.of(
            new ChartDataset("1대 주주", List.of(45.0, 45.0)),
            new ChartDataset("2대 주주", List.of(0.0, 0.0)),
            new ChartDataset("3대 주주", List.of(0.0, 0.0)),
            new ChartDataset("4대 주주", List.of(0.0, 0.0))
    ));
    private static final StockInfo STOCK = StockInfo.builder()
            .symbol(SYMBOL)
            .name(NAME)
            .build();
    public static final List<ShareResponse> SHARE_RESPONSES = List.of(ShareResponse.from(SHARE_1), ShareResponse.from(SHARE_2));
    private static final List<StockInfo> STOCKS = List.of(STOCK);
    private static final Page<StockInfo> STOCK_PAGES = new PageImpl<>(STOCKS);
    private static final PageRequest PAGE_REQUEST = PageRequest.of(1, 10);
    public static final ShareSearchResponse SHARE_SEARCH_RESPONSE = ShareSearchResponse.from(SHARE_RESPONSES);
    public static final StockResponse STOCK_RESPONSE = StockResponse.from(STOCK);
    public static final StockSearchRequest STOCK_SEARCH_REQUEST = new StockSearchRequest(SYMBOL);
    public static final StockSearchResponse STOCK_SEARCH_RESPONSE = StockSearchResponse.from(List.of(STOCK_RESPONSE));
    private static final List<Share> SHARES = List.of(SHARE_1, SHARE_2);
    @Mock
    private CorporateBoardStabilityRepository corporateBoardStabilityRepository;
    @Mock
    private DividendRepository dividendRepository;
    @Mock
    private HrrRepository hrrRepository;
    @Mock
    private ShareRepository shareRepository;
    @Mock
    private StockInfoRepository stockInfoRepository;
    @InjectMocks
    private StockService stockService;

    @Test
    void getStocks() {
        when(stockInfoRepository.findAll(any(PageRequest.class)))
                .thenReturn(STOCK_PAGES);

        assertThat(stockService.getStocks(PAGE_REQUEST))
                .isEqualTo(STOCK_PAGES);
    }

    @Test
    void getStock() {
        when(stockInfoRepository.findBySymbol(anyString()))
                .thenReturn(Optional.of(STOCK));

        assertThat(stockService.getStock(SYMBOL))
                .isEqualTo(STOCK_RESPONSE);
    }

    @Test
    void getStocksBySymbol() {
        when(stockInfoRepository.findBySymbolContains(SYMBOL))
                .thenReturn(STOCKS);

        assertThat(stockService.getStocksBySymbol(STOCK_SEARCH_REQUEST))
                .isEqualTo(STOCK_SEARCH_RESPONSE);
    }

    @Test
    void getStocksByName() {
        when(stockInfoRepository.findByNameContainsIgnoreCase(anyString()))
                .thenReturn(STOCKS);

        assertThat(stockService.getStocksByName(STOCK_SEARCH_REQUEST))
                .isEqualTo(STOCK_SEARCH_RESPONSE);
    }

    @Test
    void getSharesBySymbol() {
        when(shareRepository.findBySymbol(SYMBOL))
                .thenReturn(SHARES);

        assertThat(stockService.getSharesBySymbol(SYMBOL))
                .isEqualTo(SHARE_SEARCH_RESPONSE);
    }

    @Test
    void getDividendBySymbol() {
        when(dividendRepository.findBySymbol(anyString()))
                .thenReturn(Optional.of(DIVIDEND));

        assertThat(stockService.getDividendBySymbol(SYMBOL))
                .isEqualTo(DividendResponse.from(DIVIDEND));
    }

    @Test
    void calculateBoardStability() {
        when(corporateBoardStabilityRepository.findBySymbolAndBusinessYear(anyString(), anyString()))
                .thenReturn(Optional.of(CORPORATE_BOARD_STABILITY));

        assertThat(stockService.calculateBoardStability(SYMBOL))
                .isEqualTo(Rating.A.getGrade());
    }

    @Test
    void calculateGrowthPotential() {
        when(hrrRepository.findBySymbolAndBusinessYearAndReportCode(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(HRR));

        assertThat(stockService.calculateGrowthPotential(SYMBOL))
                .isEqualTo(Rating.A.getGrade());
    }

    @Test
    void calculateGovernance() {
        when(shareRepository.findBySymbol(SYMBOL))
                .thenReturn(SHARES);

        assertThat(stockService.calculateGovernance(SYMBOL))
                .isEqualTo(Rating.A.getGrade());
    }

    @Test
    void getDataBySymbol() {
        when(shareRepository.findBySymbol(SYMBOL))
                .thenReturn(SHARES);

        assertThat(stockService.getDataBySymbol(SYMBOL))
                .isEqualTo(CHART_DATA);
    }
}
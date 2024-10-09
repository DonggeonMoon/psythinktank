package com.dgmoonlabs.psythinktank.domain.stock.service;

import com.dgmoonlabs.psythinktank.domain.stock.dto.*;
import com.dgmoonlabs.psythinktank.domain.stock.model.*;
import com.dgmoonlabs.psythinktank.domain.stock.repository.*;
import com.dgmoonlabs.psythinktank.domain.stock.vo.ChartData;
import com.dgmoonlabs.psythinktank.domain.stock.vo.ChartDataset;
import com.dgmoonlabs.psythinktank.global.constant.CriteriaField;
import com.dgmoonlabs.psythinktank.global.constant.GrowthPotential;
import com.dgmoonlabs.psythinktank.global.constant.Pagination;
import com.dgmoonlabs.psythinktank.global.constant.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static com.dgmoonlabs.psythinktank.global.constant.CorporateBoardStability.BUSINESS_YEAR;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockInfoRepository stockInfoRepository;
    private final ShareRepository shareRepository;
    private final HrrRepository hrrRepository;
    private final DividendRepository dividendRepository;
    private final CorporateBoardStabilityRepository corporateBoardStabilityRepository;

    @Transactional(readOnly = true)
    public Page<StockInfo> getStocks(final Pageable pageable) {
        return stockInfoRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        Pagination.SIZE.getValue(),
                        Sort.by(CriteriaField.SYMBOL.getName()).ascending()
                )
        );
    }

    @Transactional(readOnly = true)
    public StockResponse getStock(final String symbol) {
        return StockResponse.from(
                stockInfoRepository.findBySymbolIgnoreCase(symbol)
                        .orElse(StockInfo.builder().build())
        );
    }

    @Transactional(readOnly = true)
    public StockSearchResponse getStocksBySymbol(final StockSearchRequest stockSearchRequest) {
        return StockSearchResponse.from(
                stockInfoRepository.findBySymbolContainsIgnoreCase(stockSearchRequest.searchText())
                        .stream()
                        .map(StockResponse::from)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public StockSearchResponse getStocksByName(final StockSearchRequest stockSearchRequest) {
        return StockSearchResponse.from(
                stockInfoRepository.findByNameContainsIgnoreCase(stockSearchRequest.searchText())
                        .stream()
                        .map(StockResponse::from)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public ShareSearchResponse getSharesBySymbol(final String symbol) {
        return ShareSearchResponse.from(shareRepository.findBySymbol(symbol)
                .stream()
                .sorted(
                        Comparator.comparing(Share::getDate)
                                .reversed()
                                .thenComparing(Share::getValue)
                                .reversed()
                )
                .map(ShareResponse::from)
                .toList()
        );
    }

    @Transactional(readOnly = true)
    public DividendResponse getDividendBySymbol(final String symbol) {
        return DividendResponse.from(
                dividendRepository.findBySymbol(symbol)
                        .orElse(Dividend.builder().value(0).build())
        );
    }

    @Transactional(readOnly = true)
    public String calculateBoardStability(final String symbol) {
        Double boardStability = corporateBoardStabilityRepository.findBySymbolAndBusinessYear(symbol, BUSINESS_YEAR.getText())
                .orElse(CorporateBoardStability.builder().build())
                .getValue();

        return Rating.evaluateBoardStability(boardStability);
    }

    @Transactional(readOnly = true)
    public String calculateGrowthPotential(final String symbol) {
        Double hrr = hrrRepository.findBySymbolAndBusinessYearAndReportCode(
                        symbol,
                        GrowthPotential.BUSINESS_YEAR.getText(),
                        GrowthPotential.REPORT_CODE.getText()
                ).orElse(Hrr.builder().build())
                .getValue();
        return Rating.evaluateGrowthPotential(hrr);
    }

    @Transactional(readOnly = true)
    public String calculateGovernance(final String symbol) {
        Double currentShare = shareRepository.findBySymbol(symbol)
                .stream()
                .max(Comparator.comparing(Share::getDate))
                .map(Share::getValue)
                .orElse(null);
        return Rating.evaluateGovernance(currentShare);
    }

    @Transactional(readOnly = true)
    public ChartData getDataBySymbol(final String symbol) {
        List<Share> shares = shareRepository.findBySymbol(symbol)
                .stream()
                .sorted(
                        Comparator.comparing(Share::getDate)
                                .reversed()
                                .thenComparing(Share::getValue)
                                .reversed()
                )
                .toList();

        List<String> dates = shares.stream()
                .map(share -> share.getDate().toString())
                .distinct()
                .toList();

        return new ChartData(
                dates,
                IntStream.range(0, 4)
                        .mapToObj(index -> new ChartDataset(
                                index + 1 + "대 주주",
                                dates.stream()
                                        .map(date -> shares.stream()
                                                .filter(it -> it.getDate().toString().equals(date))
                                                .skip(index)
                                                .map(Share::getValue)
                                                .findFirst()
                                                .orElse(0.0)
                                        ).toList()
                        ))
                        .toList()
        );
    }
}

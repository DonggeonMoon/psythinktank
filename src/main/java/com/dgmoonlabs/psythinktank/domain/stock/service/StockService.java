package com.dgmoonlabs.psythinktank.domain.stock.service;

import com.dgmoonlabs.psythinktank.domain.stock.model.Dividend;
import com.dgmoonlabs.psythinktank.domain.stock.model.Share;
import com.dgmoonlabs.psythinktank.domain.stock.model.StockInfo;
import com.dgmoonlabs.psythinktank.domain.stock.repository.*;
import com.dgmoonlabs.psythinktank.global.constant.CriteriaField;
import com.dgmoonlabs.psythinktank.global.constant.Hrr;
import com.dgmoonlabs.psythinktank.global.constant.Pagination;
import com.dgmoonlabs.psythinktank.global.constant.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockInfoRepository stockInfoRepository;
    private final ShareRepository shareRepository;
    private final HRRRepository hrrRepository;
    private final DividendRepository dividendRepository;
    private final CorporateBoardStabilityRepository corporateBoardStabilityRepository;

    @Transactional
    public Page<StockInfo> selectStocks(int page) {
        return stockInfoRepository.findAll(
                PageRequest.of(
                        page,
                        Pagination.SIZE.getValue(),
                        Sort.by(CriteriaField.SYMBOL.getName()).ascending()
                )
        );
    }

    @Transactional
    public List<StockInfo> selectStocksBySymbol(String symbol) {
        return stockInfoRepository.findBySymbolContains(symbol);
    }

    @Transactional
    public List<StockInfo> selectStocksByName(String name) {
        return stockInfoRepository.findByNameContains(name);
    }

    @Transactional
    public List<Share> selectSharesBySymbol(String symbol) {
        return shareRepository.findBySymbol(symbol);
    }

    @Transactional
    public Dividend selectDividendBySymbol(String symbol) {
        return dividendRepository.findById(symbol)
                .orElseThrow(IllegalStateException::new);
    }

    @Transactional
    public String calculateBoardStability(final String symbol) {
        Double boardStability;
        boardStability = corporateBoardStabilityRepository.findBySymbol(symbol)
                .orElseThrow(IllegalStateException::new)
                .getValue();

        return Rating.evaluateBoardStability(boardStability);
    }

    @Transactional
    public String calculateGrowthPotential(String symbol) {
        Double hrr = hrrRepository.findBySymbolAndBusinessYearAndReportCode(
                symbol,
                Hrr.BUSINESS_YEAR.getText(),
                Hrr.REPORT_CODE.getText()
        ).getValue();
        return Rating.evaluateGrowthPotential(hrr);
    }

    @Transactional
    public String calculateGovernance(final String symbol) {
        Double currentShare = shareRepository.findBySymbol(symbol)
                .stream()
                .max(Comparator.comparing(Share::getDate))
                .map(Share::getValue)
                .orElseThrow(NoSuchElementException::new);
        return Rating.evaluateGovernance(currentShare);
    }
}

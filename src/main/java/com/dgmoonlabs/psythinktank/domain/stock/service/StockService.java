package com.dgmoonlabs.psythinktank.domain.stock.service;

import com.dgmoonlabs.psythinktank.domain.stock.model.*;
import com.dgmoonlabs.psythinktank.domain.stock.repository.*;
import com.dgmoonlabs.psythinktank.global.constant.CriteriaField;
import com.dgmoonlabs.psythinktank.global.constant.Hrr;
import com.dgmoonlabs.psythinktank.global.constant.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockInfoRepository stockInfoRepository;
    private final ShareRepository shareRepository;
    private final HRRRepository hrrRepository;
    private final DividendRepository dividendRepository;
    private final CorporateBoardStabilityRepository corporateBoardStabilityRepository;

    @Transactional
    public Page<StockInfo> selectAllStockInfo(int page) {
        return stockInfoRepository.findAll(PageRequest.of(page, Pagination.SIZE.getValue(), Sort.by(CriteriaField.SYMBOL.getName()).ascending()));
    }

    @Transactional
    public List<StockInfo> selectAllStockInfoBySymbol(String symbol) {
        return stockInfoRepository.findBySymbolContains(symbol);
    }

    @Transactional
    public List<StockInfo> selectAllStockInfoByName(String name) {
        return stockInfoRepository.findByNameContains(name);
    }

    @Transactional
    public List<Share> selectAllShareBySymbol(String symbol) {
        return shareRepository.findBySymbol(symbol);
    }

    @Transactional
    public StockInfo selectOneStockInfoBySymbol(String symbol) {
        return stockInfoRepository.findById(symbol).orElseGet(StockInfo::new);
    }

    @Transactional
    public HRR selectOneHRRBySymbol(String symbol) {
        return hrrRepository.findBySymbolAndBusinessYearAndReportCode(symbol, Hrr.BUSINESS_YEAR.getText(), Hrr.REPORT_CODE.getText());
    }

    @Transactional
    public Dividend selectOneDividendBySymbol(String symbol) {
        return dividendRepository.findById(symbol).orElseGet(Dividend::new);
    }

    @Transactional
    public CorporateBoardStability selectOneCorporateBoardStabilityBySymbol(String symbol) throws Exception {
        return corporateBoardStabilityRepository.findBySymbol(symbol).orElseThrow(Exception::new);
    }
}

package com.dgmoonlabs.psythinktank.domain.stock.service;

import com.dgmoonlabs.psythinktank.domain.stock.model.*;
import com.dgmoonlabs.psythinktank.domain.stock.repository.*;
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
        return stockInfoRepository.findAll(PageRequest.of(page, 10, Sort.by("symbol").ascending()));
    }

    @Transactional
    public List<StockInfo> selectAllStockInfoBySymbol(String symbol) {
        return stockInfoRepository.findBySymbolLike("%" + symbol + "%");
    }

    @Transactional
    public List<StockInfo> selectAllStockInfoByName(String name) {
        return stockInfoRepository.findByNameLike("%" + name + "%");
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
        return hrrRepository.findBySymbolAndBusinessYearAndReportCode(symbol, "2021", "11011");
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

package com.mdg.PSYThinktank.stock.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mdg.PSYThinktank.stock.model.CorporateBoardStability;
import com.mdg.PSYThinktank.stock.model.Dividend;
import com.mdg.PSYThinktank.stock.model.HRR;
import com.mdg.PSYThinktank.stock.model.Share;
import com.mdg.PSYThinktank.stock.model.StockInfo;
import com.mdg.PSYThinktank.stock.repository.CorporateBoardStabilityRepository;
import com.mdg.PSYThinktank.stock.repository.DividendRepository;
import com.mdg.PSYThinktank.stock.repository.HRRRepository;
import com.mdg.PSYThinktank.stock.repository.ShareRepository;
import com.mdg.PSYThinktank.stock.repository.StockInfoRepository;

import lombok.RequiredArgsConstructor;

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
        return stockInfoRepository.findAll(PageRequest.of(page, 10, Sort.by("stockCode").ascending()));
    }

    @Transactional
    public List<StockInfo> selectAllStockInfoByStockCode(String stockCode) {
        return stockInfoRepository.findByStockCodeLike("%" + stockCode + "%");
    }

    @Transactional
    public List<StockInfo> selectAllStockInfoByStockName(String stockName) {
        return stockInfoRepository.findByStockNameLike("%" + stockName + "%");
    }

    @Transactional
    public List<Share> selectAllShareByStockCode(String stockCode) {
        return shareRepository.findByStockCode(stockCode);
    }

    @Transactional
    public StockInfo selectOneStockInfoByStockCode(String stockCode) {
        return stockInfoRepository.findById(stockCode).orElseGet(StockInfo::new);
    }

    @Transactional
    public HRR selectOneHRRByStockCode(String stockCode) {
        return hrrRepository.findByStockCodeAndBsnsYearAndReprtCode(stockCode, "2021", "11011");
    }

    @Transactional
    public Dividend selectOneDividendByStockCode(String stockCode) {
        return dividendRepository.findById(stockCode).orElseGet(Dividend::new);
    }

    @Transactional
    public CorporateBoardStability selectOneCorporateBoardStabilityByStockCode(String stockCode) throws Exception {
        return corporateBoardStabilityRepository.findByStockCode(stockCode).orElseThrow(Exception::new);
    }
}

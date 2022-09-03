package com.mdg.PSYThinktank.service;

import com.mdg.PSYThinktank.model.Dividend;
import com.mdg.PSYThinktank.model.HRR;
import com.mdg.PSYThinktank.model.Share;
import com.mdg.PSYThinktank.model.StockInfo;
import com.mdg.PSYThinktank.repository.DividendRepository;
import com.mdg.PSYThinktank.repository.HRRRepository;
import com.mdg.PSYThinktank.repository.ShareRepository;
import com.mdg.PSYThinktank.repository.StockInfoRepository;
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
        return stockInfoRepository.findById(stockCode).orElse(new StockInfo());
    }

    @Transactional
    public HRR selectOneHRRByStockCode(String stockCode) {
        return hrrRepository.findByStockCodeAndBsnsYearAndReprtCode(stockCode, "2021", "11011");
    }

    @Transactional
    public Dividend selectOneDividendByStockCode(String StockCode) {
        return dividendRepository.findById(StockCode).orElse(new Dividend());
    }
}

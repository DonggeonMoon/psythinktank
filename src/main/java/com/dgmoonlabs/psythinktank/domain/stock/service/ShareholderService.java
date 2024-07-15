package com.dgmoonlabs.psythinktank.domain.stock.service;

import com.dgmoonlabs.psythinktank.domain.stock.repository.mongo.ShareholderRepository;
import com.dgmoonlabs.psythinktank.global.constant.OpenDartApiName;
import com.dgmoonlabs.psythinktank.global.constant.StockHypeIndex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShareholderService {
    private final ShareholderRepository shareholderRepository;

    public String calculateStockHypeIndex(String symbol) {
        double thisYear = Double.parseDouble(
                shareholderRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                                symbol,
                                OpenDartApiName.MINOR_HOLDER_STATUS.getApiName(),
                                StockHypeIndex.BUSINESS_YEAR.getText(),
                                StockHypeIndex.REPORT_CODE.getText()
                        ).orElseThrow(RuntimeException::new)
                        .getShareholderTotalCount()
                        .replace(",", "")
        );

        double lastYear = Double.parseDouble(
                shareholderRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                                symbol,
                                OpenDartApiName.MINOR_HOLDER_STATUS.getApiName(),
                                StockHypeIndex.LAST_BUSINESS_YEAR.getText(),
                                StockHypeIndex.REPORT_CODE.getText()
                        ).orElseThrow(RuntimeException::new)
                        .getShareholderTotalCount()
                        .replace(",", "")
        );

        try {
            double stockHypeIndex = Math.round((thisYear - lastYear) / lastYear * 100.0 * 100.0) / 100.0;
            return String.valueOf(stockHypeIndex);
        } catch (Exception e) {
            return "error";
        }
    }
}

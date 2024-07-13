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
        String thisYear = shareholderRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                        symbol,
                        OpenDartApiName.MINOR_HOLDER_STATUS.getApiName(),
                        StockHypeIndex.BUSINESS_YEAR.getText(),
                        StockHypeIndex.REPORT_CODE.getText()
                ).orElseThrow(RuntimeException::new)
                .getShareholderTotalCount();

        String lastYear = shareholderRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                        symbol,
                        OpenDartApiName.MINOR_HOLDER_STATUS.getApiName(),
                        StockHypeIndex.BUSINESS_YEAR.getText(),
                        StockHypeIndex.REPORT_CODE.getText()
                ).orElseThrow(RuntimeException::new)
                .getShareholderTotalCount();
        try {
            return String.valueOf((Integer.getInteger(thisYear) - Integer.getInteger(lastYear)) / Integer.getInteger(lastYear) * 100);
        } catch (Exception e) {
            return "error";
        }
    }
}

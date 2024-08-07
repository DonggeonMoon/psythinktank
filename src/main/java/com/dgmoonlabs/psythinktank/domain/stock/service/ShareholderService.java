package com.dgmoonlabs.psythinktank.domain.stock.service;

import com.dgmoonlabs.psythinktank.domain.stock.model.opendart.Shareholder;
import com.dgmoonlabs.psythinktank.domain.stock.repository.mongo.ShareholderRepository;
import com.dgmoonlabs.psythinktank.global.constant.OpenDartApiName;
import com.dgmoonlabs.psythinktank.global.constant.StockHypeIndex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.dgmoonlabs.psythinktank.global.constant.Message.ERROR;

@Service
@RequiredArgsConstructor
public class ShareholderService {
    private final ShareholderRepository shareholderRepository;

    public String calculateStockHypeIndex(String symbol) {
        try {
            double thisYear = shareholderRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                            symbol,
                            OpenDartApiName.MINOR_HOLDER_STATUS.getApiName(),
                            StockHypeIndex.BUSINESS_YEAR.getText(),
                            StockHypeIndex.REPORT_CODE.getText()
                    ).orElse(Shareholder.emptyDocument())
                    .getShareholderTotalCount();

            double lastYear =
                    shareholderRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                                    symbol,
                                    OpenDartApiName.MINOR_HOLDER_STATUS.getApiName(),
                                    StockHypeIndex.LAST_BUSINESS_YEAR.getText(),
                                    StockHypeIndex.REPORT_CODE.getText()
                            ).orElse(Shareholder.emptyDocument())
                            .getShareholderTotalCount();

            double stockHypeIndex = Math.round((thisYear - lastYear) / lastYear * 100.0 * 100.0) / 100.0;
            return String.valueOf(stockHypeIndex);
        } catch (Exception e) {
            return ERROR.getText();
        }
    }
}

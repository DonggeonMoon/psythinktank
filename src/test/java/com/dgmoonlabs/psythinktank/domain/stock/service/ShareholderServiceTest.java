package com.dgmoonlabs.psythinktank.domain.stock.service;

import com.dgmoonlabs.psythinktank.domain.stock.model.opendart.Shareholder;
import com.dgmoonlabs.psythinktank.domain.stock.repository.mongo.ShareholderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShareholderServiceTest {
    @Autowired
    private ShareholderRepository shareholderRepository;

    @Test
    void test() {
        Shareholder shareholder = shareholderRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                "004380",
                "mrhlSttus",
                "2023",
                "11011"
        ).orElseThrow(RuntimeException::new);

        Shareholder shareholder2 = shareholderRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                "004380",
                "mrhlSttus",
                "2022",
                "11011"
        ).orElseThrow(RuntimeException::new);

        System.out.println("shareholder = " + shareholder.getShareholderTotalCount());
        System.out.println("shareholder2 = " + shareholder2.getShareholderTotalCount());
    }

}
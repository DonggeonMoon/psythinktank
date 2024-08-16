package com.dgmoonlabs.psythinktank.domain.stock.service;

import com.dgmoonlabs.psythinktank.domain.stock.model.opendart.Shareholder;
import com.dgmoonlabs.psythinktank.domain.stock.repository.mongo.ShareholderRepository;
import com.dgmoonlabs.psythinktank.global.exception.ShareholderNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
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
        ).orElseThrow(ShareholderNotExistException::new);

        Shareholder shareholder2 = shareholderRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                "004380",
                "mrhlSttus",
                "2022",
                "11011"
        ).orElseThrow(ShareholderNotExistException::new);

        log.info("shareholder counts: {}", shareholder.getShareholderTotalCount());
        log.info("shareholder2 counts: {}", shareholder2.getShareholderTotalCount());
    }

}
package com.dgmoonlabs.psythinktank.domain.stock.repository.mongo;

import com.dgmoonlabs.psythinktank.domain.stock.model.opendart.Shareholder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ShareholderRepository extends MongoRepository<Shareholder, String> {
    Optional<Shareholder> findBySymbolAndApiNameAndBusinessYearAndReportCode(String symbol, String apiName, String businessYear, String reportCode);
}

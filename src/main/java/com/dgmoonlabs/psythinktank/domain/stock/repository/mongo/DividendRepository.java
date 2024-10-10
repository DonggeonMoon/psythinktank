package com.dgmoonlabs.psythinktank.domain.stock.repository.mongo;

import com.dgmoonlabs.psythinktank.domain.stock.model.opendart.Dividend;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DividendRepository extends MongoRepository<Dividend, String> {
    Optional<Dividend> findBySymbolAndApiNameAndBusinessYearAndReportCode(String symbol, String apiName, String businessYear, String reportCode);
}

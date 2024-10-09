package com.dgmoonlabs.psythinktank.domain.stock.repository.mongo;

import com.dgmoonlabs.psythinktank.domain.stock.model.opendart.MinorShareholder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MinorShareholderRepository extends MongoRepository<MinorShareholder, String> {
    Optional<MinorShareholder> findBySymbolAndApiNameAndBusinessYearAndReportCode(String symbol, String apiName, String businessYear, String reportCode);
}

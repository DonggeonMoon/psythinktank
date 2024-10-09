package com.dgmoonlabs.psythinktank.domain.stock.repository.mongo;

import com.dgmoonlabs.psythinktank.domain.stock.model.opendart.DirectorCompensation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DirectorCompensationRepository extends MongoRepository<DirectorCompensation, String> {
    Optional<DirectorCompensation> findBySymbolAndApiNameAndBusinessYearAndReportCode(String symbol, String apiName, String businessYear, String reportCode);
}

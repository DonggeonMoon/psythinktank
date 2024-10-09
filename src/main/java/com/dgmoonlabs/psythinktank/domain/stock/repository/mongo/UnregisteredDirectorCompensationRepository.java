package com.dgmoonlabs.psythinktank.domain.stock.repository.mongo;

import com.dgmoonlabs.psythinktank.domain.stock.model.opendart.UnregisteredDirectorCompensation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UnregisteredDirectorCompensationRepository extends MongoRepository<UnregisteredDirectorCompensation, String> {
    Optional<UnregisteredDirectorCompensation> findBySymbolAndApiNameAndBusinessYearAndReportCode(String symbol, String apiName, String businessYear, String reportCode);
}

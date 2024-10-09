package com.dgmoonlabs.psythinktank.domain.stock.repository.mongo;

import com.dgmoonlabs.psythinktank.domain.stock.model.opendart.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Optional<Employee> findBySymbolAndApiNameAndBusinessYearAndReportCode(String symbol, String apiName, String businessYear, String reportCode);
}

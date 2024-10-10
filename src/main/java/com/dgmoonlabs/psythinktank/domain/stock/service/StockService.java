package com.dgmoonlabs.psythinktank.domain.stock.service;

import com.dgmoonlabs.psythinktank.domain.stock.dto.*;
import com.dgmoonlabs.psythinktank.domain.stock.model.Share;
import com.dgmoonlabs.psythinktank.domain.stock.model.StockInfo;
import com.dgmoonlabs.psythinktank.domain.stock.model.opendart.*;
import com.dgmoonlabs.psythinktank.domain.stock.repository.ShareRepository;
import com.dgmoonlabs.psythinktank.domain.stock.repository.StockInfoRepository;
import com.dgmoonlabs.psythinktank.domain.stock.repository.mongo.*;
import com.dgmoonlabs.psythinktank.domain.stock.vo.ChartData;
import com.dgmoonlabs.psythinktank.domain.stock.vo.ChartDataset;
import com.dgmoonlabs.psythinktank.global.constant.*;
import com.dgmoonlabs.psythinktank.global.constant.opendart.ApiName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static com.dgmoonlabs.psythinktank.global.constant.Message.ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
    private final StockInfoRepository stockInfoRepository;
    private final ShareRepository shareRepository;
    private final DirectorCompensationRepository directorCompensationRepository;
    private final DividendRepository dividendRepository;
    private final EmployeeRepository employeeRepository;
    private final UnregisteredDirectorCompensationRepository unregisteredDirectorCompensationRepository;
    private final MinorShareholderRepository minorShareholderRepository;

    @Transactional(readOnly = true)
    public Page<StockInfo> getStocks(final Pageable pageable) {
        return stockInfoRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        Pagination.SIZE.getValue(),
                        Sort.by(CriteriaField.SYMBOL.getName()).ascending()
                )
        );
    }

    @Transactional(readOnly = true)
    public StockResponse getStock(final String symbol) {
        return StockResponse.from(
                stockInfoRepository.findBySymbolIgnoreCase(symbol)
                        .orElse(StockInfo.builder().build())
        );
    }

    @Transactional(readOnly = true)
    public StockSearchResponse getStocksBySymbol(final StockSearchRequest stockSearchRequest) {
        return StockSearchResponse.from(
                stockInfoRepository.findBySymbolContainsIgnoreCase(stockSearchRequest.searchText())
                        .stream()
                        .map(StockResponse::from)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public StockSearchResponse getStocksByName(final StockSearchRequest stockSearchRequest) {
        return StockSearchResponse.from(
                stockInfoRepository.findByNameContainsIgnoreCase(stockSearchRequest.searchText())
                        .stream()
                        .map(StockResponse::from)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public ShareSearchResponse getSharesBySymbol(final String symbol) {
        return ShareSearchResponse.from(
                shareRepository.findBySymbol(symbol)
                        .stream()
                        .sorted(
                                Comparator.comparing(Share::getDate)
                                        .reversed()
                                        .thenComparing(Share::getValue)
                                        .reversed()
                        )
                        .map(ShareResponse::from)
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public DividendResponse getDividendBySymbol(final String symbol) {
        Dividend dividend = dividendRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                symbol,
                ApiName.DIVIDEND.getText(),
                GrowthPotential.BUSINESS_YEAR.getText(),
                GrowthPotential.REPORT_CODE.getText()
        ).orElse(Dividend.emptyDocument());

        log.info("dividend = {}", dividend.getValue());
        return DividendResponse.from(dividend);
    }

    @Transactional(readOnly = true)
    public String calculateBoardStability(final String symbol) {
        Employee employee = employeeRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                symbol,
                ApiName.EMPLOYEE.getText(),
                BoardStability.BUSINESS_YEAR.getText(),
                BoardStability.REPORT_CODE.getText()
        ).orElse(Employee.emptyDocument());

        DirectorCompensation directorCompensation = directorCompensationRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                symbol,
                ApiName.DIRECTOR_COMPENSATION.getText(),
                BoardStability.BUSINESS_YEAR.getText(),
                BoardStability.REPORT_CODE.getText()
        ).orElse(DirectorCompensation.emptyDocument());

        UnregisteredDirectorCompensation unregisteredDirectorCompensation = unregisteredDirectorCompensationRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                symbol,
                ApiName.UNREGISTERED_DIRECTOR_COMPENSATION.getText(),
                BoardStability.BUSINESS_YEAR.getText(),
                BoardStability.REPORT_CODE.getText()
        ).orElse(UnregisteredDirectorCompensation.emptyDocument());

        Double boardStability = employee.getSalaryTotalAmount()
                / (directorCompensation.getCompensationTotalAmount()
                + unregisteredDirectorCompensation.getSalaryTotalAmount());

        log.info("employee = {}", employee.getSalaryTotalAmount());
        log.info("directorCompensation = {}", directorCompensation.getCompensationTotalAmount());
        log.info("unregisteredDirectorCompensation = {}", unregisteredDirectorCompensation.getSalaryTotalAmount());
        log.info("boardStability = {}", boardStability);
        return Rating.evaluateBoardStability(boardStability);
    }

    @Transactional(readOnly = true)
    public String calculateGrowthPotential(final String symbol) {
        Employee thisYearEmployee = employeeRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                symbol,
                ApiName.EMPLOYEE.getText(),
                GrowthPotential.BUSINESS_YEAR.getText(),
                GrowthPotential.REPORT_CODE.getText()
        ).orElse(Employee.emptyDocument());

        Employee previousYearEmployee = employeeRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                symbol,
                ApiName.EMPLOYEE.getText(),
                GrowthPotential.PREVIOUS_BUSINESS_YEAR.getText(),
                GrowthPotential.REPORT_CODE.getText()
        ).orElse(Employee.emptyDocument());

        Double hrr = (thisYearEmployee.getTotalEmployeeCount() - previousYearEmployee.getTotalEmployeeCount())
                / previousYearEmployee.getTotalEmployeeCount() * 100;

        log.info("thisYearEmployee = {}", thisYearEmployee.getTotalEmployeeCount());
        log.info("previousYearEmployee = {}", previousYearEmployee.getTotalEmployeeCount());
        log.info("hrr = {}", hrr);
        return Rating.evaluateGrowthPotential(hrr);
    }

    @Transactional(readOnly = true)
    public String calculateGovernance(final String symbol) {
        Double currentShare = shareRepository.findBySymbol(symbol)
                .stream()
                .max(Comparator.comparing(Share::getDate))
                .map(Share::getValue)
                .orElse(null);
        return Rating.evaluateGovernance(currentShare);
    }

    public String calculateStockHypeIndex(String symbol) {
        try {
            double thisYear = minorShareholderRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                            symbol,
                            OpenDartApiName.MINOR_HOLDER_STATUS.getApiName(),
                            StockHypeIndex.BUSINESS_YEAR.getText(),
                            StockHypeIndex.REPORT_CODE.getText()
                    ).orElse(MinorShareholder.emptyDocument())
                    .getShareholderTotalCount();

            double lastYear =
                    minorShareholderRepository.findBySymbolAndApiNameAndBusinessYearAndReportCode(
                                    symbol,
                                    OpenDartApiName.MINOR_HOLDER_STATUS.getApiName(),
                                    StockHypeIndex.LAST_BUSINESS_YEAR.getText(),
                                    StockHypeIndex.REPORT_CODE.getText()
                            ).orElse(MinorShareholder.emptyDocument())
                            .getShareholderTotalCount();

            double stockHypeIndex = Math.round((thisYear - lastYear) / lastYear * 100.0 * 100.0) / 100.0;
            return String.valueOf(stockHypeIndex);
        } catch (Exception e) {
            return ERROR.getText();
        }
    }

    @Transactional(readOnly = true)
    public ChartData getDataBySymbol(final String symbol) {
        List<Share> shares = shareRepository.findBySymbol(symbol)
                .stream()
                .sorted(
                        Comparator.comparing(Share::getDate)
                                .reversed()
                                .thenComparing(Share::getValue)
                                .reversed()
                )
                .toList();

        List<String> dates = shares.stream()
                .map(share -> share.getDate().toString())
                .distinct()
                .toList();

        return new ChartData(
                dates,
                IntStream.range(0, 4)
                        .mapToObj(index -> new ChartDataset(
                                index + 1 + "대 주주",
                                dates.stream()
                                        .map(date -> shares.stream()
                                                .filter(it -> it.getDate().toString().equals(date))
                                                .skip(index)
                                                .map(Share::getValue)
                                                .findFirst()
                                                .orElse(0.0)
                                        ).toList()
                        ))
                        .toList()
        );
    }
}

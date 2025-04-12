package com.dgmoonlabs.psythinktank.domain.stock.model.opendart;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "open_dart")
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Employee {
    @Id
    private String id;
    private String symbol;
    @Field(name = "api_name")
    private String apiName;
    @Field(name = "corp_code")
    private String corporationCode;
    @Field(name = "bsns_year")
    private String businessYear;
    @Field(name = "reprt_code")
    private String reportCode;
    private List<EmployeeData> data;

    public static Employee emptyDocument() {
        return Employee.builder()
                .data(
                        List.of(
                                EmployeeData.builder()
                                        .salaryTotalAmount("0")
                                        .totalEmployeeCount("0")
                                        .build()
                        )
                ).build();
    }

    public Double getTotalEmployeeCount() {
        if (data == null) {
            return 0.0;
        }
        return data.stream()
                .filter(EmployeeData::isNotSubTotal)
                .map(employeeData -> employeeData.totalEmployeeCount)
                .filter(count -> !"-".equals(count))
                .map(count -> count.replace(",", ""))
                .mapToDouble(Double::parseDouble)
                .sum();
    }

    public Double getSalaryTotalAmount() {
        if (data == null) {
            return 0.0;
        }
        return data.stream()
                .filter(EmployeeData::isNotSubTotal)
                .map(employeeData -> employeeData.salaryTotalAmount)
                .filter(count -> !"-".equals(count))
                .map(count -> count.replace(",", ""))
                .mapToDouble(Double::parseDouble)
                .sum();
    }

    @Builder
    static class EmployeeData {
        private ObjectId id;
        @Field(name = "fo_bbm")
        private String businessSector;
        @Field(name = "sm")
        private String totalEmployeeCount;
        @Field(name = "fyer_salary_totamt")
        private String salaryTotalAmount;

        public boolean isNotSubTotal() {
            if (businessSector == null) {
                return true;
            }

            return !businessSector.contains("합계") && !businessSector.contains("총계");
        }
    }
}

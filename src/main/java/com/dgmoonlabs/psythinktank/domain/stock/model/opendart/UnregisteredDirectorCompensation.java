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
public class UnregisteredDirectorCompensation {
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
    private List<UnregisteredDirectorCompensationData> data;

    public static UnregisteredDirectorCompensation emptyDocument() {
        return UnregisteredDirectorCompensation.builder()
                .data(
                        List.of(
                                UnregisteredDirectorCompensationData.builder()
                                        .salaryTotalAmount("0")
                                        .build()
                        )
                )
                .build();
    }

    public Double getSalaryTotalAmount() {
        if (data == null) {
            return 0.0;
        }
        return data.stream()
                .map(directorCompensationData -> directorCompensationData.salaryTotalAmount)
                .filter(count -> !"-".equals(count))
                .map(count -> count.replace(",", ""))
                .mapToDouble(Double::parseDouble)
                .sum();
    }

    @Builder
    static class UnregisteredDirectorCompensationData {
        private ObjectId id;
        @Field(name = "fyer_salary_totamt")
        private String salaryTotalAmount;
    }
}

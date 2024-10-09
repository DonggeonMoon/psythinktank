package com.dgmoonlabs.psythinktank.domain.stock.model.opendart;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.util.StringUtils;

import java.util.List;

@Document(collection = "open_dart")
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Dividend {
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
    private List<DividendData> data;

    public static Dividend emptyDocument() {
        return Dividend.builder()
                .data(
                        List.of(
                                DividendData.builder()
                                        .value("0")
                                        .build()
                        )
                )
                .build();
    }

    public Double getValue() {
        if (data == null) {
            return 0.0;
        }
        return data.stream()
                .filter(DividendData::isCashDividendPerShare)
                .filter(DividendData::isCommonShare)
                .map(dividendData -> dividendData.value)
                .filter(count -> !"-".equals(count))
                .map(count -> count.replace(",", ""))
                .mapToDouble(Double::parseDouble)
                .sum();
    }

    @Builder
    static class DividendData {
        private ObjectId id;
        @Field(name = "se")
        private String segmentation;
        @Field(name = "stock_knd")
        private String stockKind;
        @Field(name = "thstrm")
        private String value;

        public boolean isCashDividendPerShare() {
            return "주당 현금배당금(원)".equals(segmentation);
        }

        public boolean isCommonShare() {
            if (!StringUtils.hasText(stockKind)) {
                return false;
            }
            return stockKind.contains("보통");
        }
    }
}

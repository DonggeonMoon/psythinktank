package com.dgmoonlabs.psythinktank.domain.stock.model.opendart;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "open_dart")
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Shareholder {
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
    private ShareholderData data;

    public static Shareholder from() {
        return Shareholder.builder()
                .build();
    }

    public static Shareholder emptyDocument() {
        return Shareholder.builder().build();
    }

    public Double getShareholderTotalCount() {
        if (data == null || data.shareholderTotalCount == null) {
            return 0.0;
        }
        return Double.parseDouble(data.shareholderTotalCount.replace(",", ""));
    }

    @Builder
    public static class ShareholderData {
        private ObjectId id;
        @Field(name = "shrholdr_tot_co")
        private String shareholderTotalCount;
    }
}

package com.dgmoonlabs.psythinktank.domain.stock.model.opendart;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "open_dart")
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

    public String getShareholderTotalCount() {
        return data.shareholderTotalCount;
    }

    public static class ShareholderData {
        private ObjectId id;
        @Field(name = "shrholdr_tot_co")
        private String shareholderTotalCount;
    }
}

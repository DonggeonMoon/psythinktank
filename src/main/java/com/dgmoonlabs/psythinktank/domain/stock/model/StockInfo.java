package com.dgmoonlabs.psythinktank.domain.stock.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "stock_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_code", nullable = false, length = 6)
    private String stockCode;

    @Column(name = "stock_name", nullable = false, length = 40)
    private String stockName;

    @Column(name = "corp_cls", nullable = false, length = 10)
    private String corpCls;

    @Column(name = "corp_code", nullable = false, length = 8)
    private String corpCode;

    @Column(name = "overview", columnDefinition = "TEXT")
    private String overview;
}

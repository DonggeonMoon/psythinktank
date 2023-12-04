package com.dgmoonlabs.psythinktank.domain.stock.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "corporation_board_stability")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CorporateBoardStability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_code", length = 6, columnDefinition = "char")
    private String symbol;

    @Column(name = "stock_name", length = 40)
    private String stockName;

    @Column(name = "corp_cls", length = 10)
    private String corporationClass;

    @Column(name = "bsns_year", length = 4, columnDefinition = "char")
    private String businessYear;

    @Column(name = "reprt_code", length = 5, columnDefinition = "char")
    private String reportCode;

    @Column(name = "ubmsta")
    private Long ubmsta;

    @Column(name = "bmaasta")
    private Long bmaasta;

    @Column(name = "esta")
    private Long esta;

    @Column(name = "board_stability")
    private Double value;
}

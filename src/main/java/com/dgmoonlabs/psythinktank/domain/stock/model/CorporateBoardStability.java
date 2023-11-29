package com.dgmoonlabs.psythinktank.domain.stock.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "corporation_board_stability")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CorporateBoardStability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "stock_code", length = 6, columnDefinition = "char")
    private String stockCode;
    @Column(name = "stock_name", length = 40)
    private String stockName;
    @Column(name = "corp_cls", length = 10)
    private String corp_cls;
    @Column(name = "bsns_year", length = 4, columnDefinition = "char")
    private String bsns_year;
    @Column(name = "reprt_code", length = 5, columnDefinition = "char")
    private String reprt_code;
    @Column(name = "ubmsta")
    private Long ubmsta;
    @Column(name = "bmaasta")
    private Long bmaasta;
    @Column(name = "esta")
    private Long esta;
    @Column(name = "board_stability")
    private Double boardStability;
}

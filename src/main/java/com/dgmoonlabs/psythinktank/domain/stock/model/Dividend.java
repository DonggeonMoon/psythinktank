package com.dgmoonlabs.psythinktank.domain.stock.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Data
public class Dividend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_code", nullable = false, length = 6)
    private String symbol;

    @Column(name = "stock_name", nullable = false, length = 40)
    private String stockName;

    @Column(name = "dividend", nullable = false)
    private Integer value;
}
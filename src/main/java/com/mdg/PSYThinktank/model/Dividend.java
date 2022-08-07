package com.mdg.PSYThinktank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dividend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_code", nullable = false, length = 6)
    private String stockCode;

    @Column(name = "stock_name", nullable = false, length = 40)
    private String stockName;

    @Column(name = "dividend", nullable = false)
    private Integer dividend;
}
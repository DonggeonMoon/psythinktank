package com.dgmoonlabs.psythinktank.domain.stock.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Hrr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, length = 6)
    private String symbol;

    @Column(name = "stock_name", nullable = false, length = 40)
    private String stockName;

    @Column(name = "corp_cls", nullable = false, length = 10)
    private String corporationClass;

    @Column(name = "bsns_year", nullable = false, length = 4)
    private String businessYear;

    @Column(name = "reprt_code", nullable = false, length = 5)
    private String reportCode;

    @Column(name = "total_emp")
    private String totalEmployee;

    @Column(name = "total_emp_chg")
    private String totalEmployeeChange;

    @Column(name = "hrr")
    private Double value;
}

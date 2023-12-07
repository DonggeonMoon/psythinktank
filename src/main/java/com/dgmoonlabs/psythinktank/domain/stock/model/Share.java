package com.dgmoonlabs.psythinktank.domain.stock.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Share {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_id")
    private Long id;

    @Column(name = "stock_code", length = 6)
    private String symbol;

    @Column(name = "stock_name", length = 40, nullable = false)
    private String stockName;

    @Column(nullable = false)
    private Date date;

    @Column(name = "holder_name", length = 100, nullable = false)
    private String holderName;

    @Column(name = "share", nullable = false)
    private Double value;
}

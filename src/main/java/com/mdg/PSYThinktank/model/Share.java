package com.mdg.PSYThinktank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Share {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "share_id")
	private int shareId;

	@Column(name = "stock_code", length = 6)
	private String stockCode;

	@Column(name = "stock_name", length = 40, nullable = false)
	private String stockName;

	@Column(nullable = false)
	private Date date;

	@Column(name = "holder_name", length = 100, nullable = false)
	private String holderName;

	@Column(nullable = false)
	private Double share;
}

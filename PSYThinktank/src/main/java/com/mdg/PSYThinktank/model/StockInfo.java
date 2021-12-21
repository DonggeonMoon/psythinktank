package com.mdg.PSYThinktank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "stock_info")
@Data
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

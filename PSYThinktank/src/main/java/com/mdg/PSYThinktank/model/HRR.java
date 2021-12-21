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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HRR {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hrr_id")
	private int hrrId;

	@Column(name = "stock_code", nullable = false, length = 6)
	private String stockCode;

	@Column(name = "stock_name", nullable = false, length = 40)
	private String stockName;

	@Column(name = "corp_cls", nullable = false, length = 10)
	private String corpCls;

	@Column(name = "bsns_year", nullable = false, length = 4)
	private String bsnsYear;

	@Column(name = "reprt_code", nullable = false, length = 5)
	private String reprtCode;

	@Column(name = "total_emp", nullable = true)
	private String totalEmp;

	@Column(name = "total_emp_chg", nullable = true)
	private String totalEmpChg;

	@Column(name = "hrr", nullable = true)
	private Double hrr;
}

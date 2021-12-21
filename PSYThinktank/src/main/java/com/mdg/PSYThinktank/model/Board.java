package com.mdg.PSYThinktank.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_no")
	private int boardNo;

	@Column(name = "member_id", nullable = false)
	private String memberId;

	@Column(name = "board_title", nullable = false, length = 200)
	private String boardTitle;

	@Column(name = "board_content", columnDefinition = "TEXT")
	private String boardContent;

	@ColumnDefault("0")
	@Column(name = "board_hit")
	private int boardHit;
	
	@Builder.Default
	@Column(name = "is_notice")
	private Boolean isNotice = false;

	@CreationTimestamp
	@Column(name = "write_date")
	private Timestamp writeDate;
}

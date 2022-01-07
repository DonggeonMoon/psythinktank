package com.mdg.PSYThinktank.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Circular {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "circular_id")
	private Integer circularId;
	
	@Column(name="circular_title", nullable = false, length = 100)
	private String circularTitle;
	
	@Column(name = "file_name", nullable = false, length = 200)
	private String fileName;

	@CreationTimestamp
	@Column(name = "upload_date")
	private Timestamp uploadDate;
}

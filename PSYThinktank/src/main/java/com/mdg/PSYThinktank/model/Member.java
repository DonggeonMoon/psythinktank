package com.mdg.PSYThinktank.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
	@Id
	@Column(name = "member_id", nullable = false, length = 50)
	private String memberId;

	@Column(name = "member_pw", nullable = false, length = 100)
	private String memberPw;

	@Column(name = "member_email", nullable = false, length = 100)
	private String memberEmail;
	
	@CreationTimestamp
	@Column(name = "register_date", nullable = false, length = 50)
	private Date registerDate;

	@Column(name = "user_level", nullable = false)
	private int userLevel;
}

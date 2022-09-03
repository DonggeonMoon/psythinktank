package com.mdg.PSYThinktank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Column(name = "board_no")
    private int boardNo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private int commentNo;

    @Column(name = "comment_parent")
    private int commentParent;

    @Column(name = "comment_depth", nullable = false)
    private int commentDepth;

    @Column(name = "comment_seq", nullable = false)
    private int commentSeq;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "comment_content", columnDefinition = "TEXT")
    private String commentContent;

    @CreationTimestamp
    @Column(name = "comment_date")
    private Timestamp commentDate;
}
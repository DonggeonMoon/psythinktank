package com.dgmoonlabs.psythinktank.domain.comment.model;

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
    private Long boardId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long id;

    @Column(name = "comment_parent")
    private int parent;

    @Column(name = "comment_depth", nullable = false)
    private int depth;

    @Column(name = "comment_seq", nullable = false)
    private int sequence;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "comment_content", columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(name = "comment_date")
    private Timestamp createdAt;
}

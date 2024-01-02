package com.dgmoonlabs.psythinktank.domain.comment.model;

import com.dgmoonlabs.psythinktank.domain.comment.dto.CommentRequest;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Comment {
    @Column(name = "board_no")
    private Long boardId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long id;

    @Column(name = "comment_parent")
    private Long parent;

    @Column(name = "comment_depth", nullable = false)
    private int depth;

    @Column(name = "comment_seq", nullable = false)
    private long sequence;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "comment_content", columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(name = "comment_date")
    private Timestamp createdAt;

    public void update(final CommentRequest request) {
        this.content = request.content();
    }
}

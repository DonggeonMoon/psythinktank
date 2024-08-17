package com.dgmoonlabs.psythinktank.domain.comment.model;

import com.dgmoonlabs.psythinktank.domain.comment.dto.CommentRequest;
import com.dgmoonlabs.psythinktank.global.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Comment extends BaseEntity {
    @Column(name = "article_id")
    private Long articleId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long parent;

    @Column(nullable = false)
    private int depth;

    @Column(nullable = false)
    private long sequence;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(columnDefinition = "TEXT")
    private String content;

    public void update(final CommentRequest request) {
        this.content = request.content();
    }
}

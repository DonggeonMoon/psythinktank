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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "article_id")
    private Long articleId;
    private Long parent;
    private int depth;
    private long sequence;
    @Column(name = "member_id")
    private String memberId;
    @Column(columnDefinition = "TEXT")
    private String content;

    public void update(final CommentRequest request) {
        this.content = request.content();
    }
}

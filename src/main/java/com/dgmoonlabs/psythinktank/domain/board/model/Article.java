package com.dgmoonlabs.psythinktank.domain.board.model;

import com.dgmoonlabs.psythinktank.domain.board.dto.ArticleRequest;
import com.dgmoonlabs.psythinktank.global.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("0")
    @Column
    private int hit;

    @Builder.Default
    @Column(name = "is_notice", nullable = false)
    private Boolean isNotice = false;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public void update(ArticleRequest request) {
        this.title = request.title();
        this.content = request.content();
        this.isNotice = request.isNotice();
    }

    public void increaseHit() {
        this.hit++;
    }
}

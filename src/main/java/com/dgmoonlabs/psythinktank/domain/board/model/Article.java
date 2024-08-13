package com.dgmoonlabs.psythinktank.domain.board.model;

import com.dgmoonlabs.psythinktank.domain.board.dto.ArticleRequest;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "board_title", nullable = false, length = 200)
    private String title;

    @Column(name = "board_content", columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("0")
    @Column(name = "board_hit")
    private int hit;

    @Builder.Default
    @Column(name = "is_notice", nullable = false)
    private Boolean isNotice = false;

    @CreationTimestamp
    @Column(name = "write_date")
    private Timestamp createdAt;

    public void update(ArticleRequest request) {
        this.title = request.title();
        this.content = request.title();
        this.isNotice = request.isNotice();
    }
}

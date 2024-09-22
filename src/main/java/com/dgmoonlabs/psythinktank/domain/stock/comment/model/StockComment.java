package com.dgmoonlabs.psythinktank.domain.stock.comment.model;

import com.dgmoonlabs.psythinktank.domain.stock.comment.dto.StockCommentRequest;
import com.dgmoonlabs.psythinktank.global.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "stock_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class StockComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "stock_id")
    private Long stockId;
    private Long parent;
    private int depth;
    private long sequence;
    @Column(name = "member_id")
    private String memberId;
    @Column(columnDefinition = "TEXT")
    private String content;

    public void update(final StockCommentRequest request) {
        this.content = request.content();
    }
}

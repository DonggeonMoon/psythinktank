package com.dgmoonlabs.psythinktank.domain.board.model;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardRequest;
import com.dgmoonlabs.psythinktank.global.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder.Default
    @Column(name = "is_public")
    private Boolean isPublic = true;

    @OneToMany(mappedBy = "board")
    private List<Article> articles;

    public void update(BoardRequest request) {
        this.name = request.name();
        this.isPublic = request.isPublic();
    }
}

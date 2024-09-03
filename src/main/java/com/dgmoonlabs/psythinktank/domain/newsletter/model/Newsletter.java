package com.dgmoonlabs.psythinktank.domain.newsletter.model;

import com.dgmoonlabs.psythinktank.global.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Newsletter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(name = "file_name", nullable = false, length = 200)
    private String fileName;
}

package com.dgmoonlabs.psythinktank.domain.circular.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Data
public class Circular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "circular_id")
    private Long id;

    @Column(name = "circular_title", nullable = false, length = 100)
    private String title;

    @Column(name = "file_name", nullable = false, length = 200)
    private String fileName;

    @CreationTimestamp
    @Column(name = "upload_date")
    private Timestamp createdAt;
}

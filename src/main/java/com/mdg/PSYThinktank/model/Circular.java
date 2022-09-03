package com.mdg.PSYThinktank.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Circular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "circular_id")
    private Integer circularId;

    @Column(name = "circular_title", nullable = false, length = 100)
    private String circularTitle;

    @Column(name = "file_name", nullable = false, length = 200)
    private String fileName;

    @CreationTimestamp
    @Column(name = "upload_date")
    private Timestamp uploadDate;
}

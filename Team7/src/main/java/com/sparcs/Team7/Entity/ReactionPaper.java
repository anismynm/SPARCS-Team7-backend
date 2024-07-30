package com.sparcs.Team7.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "ReactionPaper")
public class ReactionPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rp_id", nullable = false)
    private Integer rpId;

    @Column(name = "rp_title", nullable = false, length = 50)
    private String rpTitle;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "rp_text", nullable = false, columnDefinition = "LONGTEXT")
    private String rpText;

    @Column(name = "like_count", nullable = false, columnDefinition = "int default 0")
    private Integer likeCount = 0;

    @Column(name = "rp_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime rpDate;

    @Column(name = "book_title", nullable = false)
    private String bookTitle;
}


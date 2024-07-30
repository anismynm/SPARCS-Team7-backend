package com.sparcs.Team7.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "ReactionPaper")
public class ReactionPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RP_id", nullable = false)
    private Integer rpId;

    @Column(name = "RP_title", nullable = false, length = 50)
    private String rpTitle;

    @ManyToOne
    @JoinColumn(name = "email", nullable = false)
    private User user;

    @Column(name = "RP_text", nullable = false, columnDefinition = "LONGTEXT")
    private String rpText;

    @Column(name = "image_id", nullable = false, length = 50)
    private String imageId;

    @Column(name = "LikeCount", nullable = false)
    private Integer likeCount;

    @Column(name = "RP_date", nullable = false)
    private LocalDateTime rpDate;

    @ManyToOne
    @JoinColumn(name = "book_title", nullable = false)
    private Book book;

}


package com.sparcs.Team7.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "book_title", nullable = false)
    private String bookTitle;

    @Column(name = "book_synop", nullable = false, columnDefinition = "LONGTEXT")
    private String bookSynop;

    @Column(name = "book_prompt")
    private String bookPrompt;

}


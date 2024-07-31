package com.sparcs.Team7.Repository;

import com.sparcs.Team7.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    @Query(value = "select book_prompt from book where book_title = :name", nativeQuery = true)
    String getPrompt(@Param("name") String book_title);

    @Query(value = "select book_title from book", nativeQuery = true)
    List<String> find();
}

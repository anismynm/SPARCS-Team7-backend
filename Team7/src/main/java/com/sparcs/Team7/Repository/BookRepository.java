package com.sparcs.Team7.Repository;

import com.sparcs.Team7.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, String> {
    @Query(value = "select book_synop from book where book_title = :name", nativeQuery = true)
    public String getSynop(@Param("name") String book_title);
}

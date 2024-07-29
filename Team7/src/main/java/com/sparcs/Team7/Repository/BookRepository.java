package com.sparcs.Team7.Repository;

import com.sparcs.Team7.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}

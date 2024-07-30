package com.sparcs.Team7.Service;

import com.sparcs.Team7.DTO.rpsaveDTO;
import com.sparcs.Team7.Repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public String getSynop(String book_title) {
        return bookRepository.getSynop(book_title);
    }
}
package com.sparcs.Team7.Service;

import com.sparcs.Team7.DTO.rpsaveDTO;
import com.sparcs.Team7.Entity.Book;
import com.sparcs.Team7.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public String getPrompt(String book_title) {
        return bookRepository.getPrompt(book_title);
    }

    public List<String> getBooks() {
        return bookRepository.find();
    }
}
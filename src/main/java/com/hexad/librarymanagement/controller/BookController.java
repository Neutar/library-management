package com.hexad.librarymanagement.controller;

import com.hexad.librarymanagement.controller.response.BookResponse;
import com.hexad.librarymanagement.mapper.BookMapper;
import com.hexad.librarymanagement.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper = BookMapper.BOOK_MAPPER;

    @GetMapping
    public List<BookResponse> getAllBooksFromLibrary() {
        return bookMapper.mapBookResponseListFrom(bookService.getAllBooks());
    }
}

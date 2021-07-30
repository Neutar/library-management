package com.hexad.librarymanagement.book.service;

import com.hexad.librarymanagement.book.service.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();
}

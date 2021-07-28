package com.hexad.library.service;

import com.hexad.library.domain.Book;
import com.hexad.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}

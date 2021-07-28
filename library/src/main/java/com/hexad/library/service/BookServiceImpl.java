package com.hexad.library.service;

import com.hexad.library.mapper.BookMapper;
import com.hexad.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private static final BookMapper bookMapper = BookMapper.BOOK_MAPPER;
    private final BookRepository bookRepository;

    @Override
    public List<BookDto> getAllBooks() {
        return bookMapper.mapBookDtoListFrom(bookRepository.findAll());
    }
}

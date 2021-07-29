package com.hexad.librarymanagement.service;

import com.hexad.librarymanagement.mapper.BookMapper;
import com.hexad.librarymanagement.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private static final BookMapper bookMapper = BookMapper.BOOK_MAPPER;
    private final BookRepository bookRepository;

    @Override
    public List<BookDto> getAllBooks() {
        return bookMapper.mapBookDtoListFrom(bookRepository.findAll());
    }
}

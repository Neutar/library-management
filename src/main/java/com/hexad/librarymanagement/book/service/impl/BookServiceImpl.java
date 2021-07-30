package com.hexad.librarymanagement.book.service.impl;


import com.hexad.librarymanagement.book.mapper.BookMapper;
import com.hexad.librarymanagement.book.repository.BookRepository;
import com.hexad.librarymanagement.book.service.BookService;
import com.hexad.librarymanagement.book.service.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private static final BookMapper bookMapper = BookMapper.BOOK_MAPPER;
    private final BookRepository bookRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookMapper.mapBookDtoListFrom(bookRepository.findAllByBorrowedIsFalse());
    }
}

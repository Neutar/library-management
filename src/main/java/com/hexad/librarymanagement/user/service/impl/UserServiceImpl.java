package com.hexad.librarymanagement.user.service.impl;

import com.hexad.librarymanagement.book.domain.Book;
import com.hexad.librarymanagement.book.exception.BookNotFoundException;
import com.hexad.librarymanagement.book.mapper.BookMapper;
import com.hexad.librarymanagement.book.repository.BookRepository;
import com.hexad.librarymanagement.book.service.dto.BookDto;
import com.hexad.librarymanagement.user.domain.User;
import com.hexad.librarymanagement.user.exception.UserNotFoundException;
import com.hexad.librarymanagement.user.repository.UserRepository;
import com.hexad.librarymanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final BookMapper bookMapper = BookMapper.BOOK_MAPPER;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<BookDto> borrowBook(UUID userId, UUID bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        user.borrowBook(book);
        userRepository.save(user);
        return bookMapper.mapBookDtoListFrom(user.getBorrowedBookList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<BookDto> returnBook(UUID userId, List<UUID> bookIdList) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        List<Book> books = bookRepository.findAllById(bookIdList);
        books.forEach(user::returnBook);
        userRepository.save(user);
        return bookMapper.mapBookDtoListFrom(user.getBorrowedBookList());
    }
}

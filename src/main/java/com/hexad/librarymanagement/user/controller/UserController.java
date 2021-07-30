package com.hexad.librarymanagement.user.controller;

import com.hexad.librarymanagement.book.controller.response.BookResponse;
import com.hexad.librarymanagement.book.mapper.BookMapper;
import com.hexad.librarymanagement.user.exception.UserNotFoundException;
import com.hexad.librarymanagement.user.mapper.UserMapper;
import com.hexad.librarymanagement.user.repository.UserRepository;
import com.hexad.librarymanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private static final BookMapper bookMapper = BookMapper.BOOK_MAPPER;
    private final UserService userService;
    private final UserRepository userRepository;

    @PutMapping("/{userId}/book/{bookId}")
    public List<BookResponse> borrowBook(@PathVariable UUID userId, @PathVariable UUID bookId) {
        return bookMapper.mapBookResponseListFrom(userService.borrowBook(userId, bookId));
    }
}
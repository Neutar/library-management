package com.hexad.librarymanagement.user.controller;

import com.hexad.librarymanagement.book.controller.response.BookResponse;
import com.hexad.librarymanagement.book.mapper.BookMapper;
import com.hexad.librarymanagement.user.controller.response.UserResponse;
import com.hexad.librarymanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private static final BookMapper bookMapper = BookMapper.BOOK_MAPPER;
    private final UserService userService;

    @PutMapping("/{userId}/book/{bookId}")
    public List<BookResponse> borrowBook(@PathVariable UUID userId, @PathVariable UUID bookId) {
        return bookMapper.mapBookResponseListFrom(userService.borrowBook(userId, bookId));
    }


    @PostMapping("/{userId}/book/return")
    public List<BookResponse> returnBook(@PathVariable UUID userId, @RequestBody List<UUID> bookIds) {
        return bookMapper.mapBookResponseListFrom(userService.returnBook(userId, bookIds));
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return null;
    }
}

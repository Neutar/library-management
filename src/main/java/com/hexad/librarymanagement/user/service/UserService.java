package com.hexad.librarymanagement.user.service;

import com.hexad.librarymanagement.book.service.dto.BookDto;
import com.hexad.librarymanagement.user.service.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<BookDto> borrowBook(UUID userId, UUID bookId);

    List<BookDto> returnBook(UUID userId, List<UUID> bookIdList);

    List<UserDto> getAllUsers();
}

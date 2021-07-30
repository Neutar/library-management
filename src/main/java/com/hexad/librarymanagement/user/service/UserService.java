package com.hexad.librarymanagement.user.service;

import com.hexad.librarymanagement.book.service.dto.BookDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<BookDto> borrowBook(UUID userId, UUID bookId);
}

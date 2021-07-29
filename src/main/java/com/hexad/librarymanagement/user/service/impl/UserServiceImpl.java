package com.hexad.librarymanagement.user.service.impl;

import com.hexad.librarymanagement.book.service.dto.BookDto;
import com.hexad.librarymanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public class UserServiceImpl implements UserService {
    @Override
    public List<BookDto> borrowBook(UUID userId, UUID bookId) {
        return null;
    }
}

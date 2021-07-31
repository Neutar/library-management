package com.hexad.librarymanagement.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexad.librarymanagement.book.domain.Book;
import com.hexad.librarymanagement.book.repository.BookRepository;
import com.hexad.librarymanagement.user.domain.User;
import com.hexad.librarymanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestDataServiceImpl implements TestDataService {

    @Value("${initial.test.data.path.book}")
    private String initialBookDataPath;
    @Value("${initial.test.data.path.user}")
    private String initialUserDataPath;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void initializeData() throws IOException {
        Path bookPath = Paths.get(initialBookDataPath);
        if(bookPath.toFile().exists()) {
            List<Book> books = Files.readAllLines(bookPath)
                    .stream()
                    .map(this::mapToBook)
                    .collect(Collectors.toList());

            bookRepository.saveAll(books);
        }
        Path userPath = Paths.get(initialUserDataPath);
        if(userPath.toFile().exists()) {
            List<User> users = Files.readAllLines(Paths.get(initialUserDataPath))
                    .stream()
                    .map(this::mapToUser)
                    .collect(Collectors.toList());

            userRepository.saveAll(users);
        }

    }

    private User mapToUser(String user) {
        try {
            return objectMapper.readValue(user, User.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("User couldn't parsed.");
        }
    }

    private Book mapToBook(String book) {
        try {
            return objectMapper.readValue(book, Book.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Book couldn't parsed.");
        }
    }
}

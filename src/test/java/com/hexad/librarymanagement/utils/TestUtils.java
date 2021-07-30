package com.hexad.librarymanagement.utils;

import com.hexad.librarymanagement.book.domain.Book;
import com.hexad.librarymanagement.book.service.dto.BookDto;
import com.hexad.librarymanagement.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {
    public static Book buildBook(UUID id, String author, String bookName, String isbn) {
        return Book.builder().id(UUID.randomUUID()).author(author).name(bookName).isbn(isbn).build();
    }

    public static BookDto buildBookDto(UUID id, String author, String bookName, String isbn) {
        return BookDto.builder().id(id).author(author).name(bookName).isbn(isbn).build();
    }

    public static User buildUser(UUID userId, String userName, Book... books) {
        return User.builder().id(userId).name(userName).borrowedBookList(new ArrayList<>(Arrays.asList(books))).build();
    }
}

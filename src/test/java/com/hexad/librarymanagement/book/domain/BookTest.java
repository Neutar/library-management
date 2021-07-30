package com.hexad.librarymanagement.book.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hexad.librarymanagement.utils.TestUtils.buildBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookTest {

    @Test
    void borrowBook_shouldDecreaseCopyCountBy1_whenThereIsABook() {
        //given:
        Book book = buildBook(UUID.randomUUID(), "Stephen King", "The Shining", "9783785746042", 3L);

        //when:
        book.borrowBook();

        //then:
        assertEquals(2, book.getCopyCount());
    }
}

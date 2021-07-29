package com.hexad.librarymanagement.book.domain;

import org.junit.jupiter.api.Test;

import static com.hexad.librarymanagement.utils.TestUtils.buildBook;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookTest {

    @Test
    void borrowBook_shouldMarkBookAsBorrowed_whenExists() {
        //given:
        Book book = buildBook("Stephen King", "The Shining", "9783785746042");

        //when:
        book.borrowBook();

        //then:
        assertTrue(book.isBorrowed());
    }
}

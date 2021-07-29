package com.hexad.librarymanagement.user.domain;

import com.hexad.librarymanagement.book.domain.Book;
import com.hexad.librarymanagement.user.exception.ExceededBorrowedBookLimitException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hexad.librarymanagement.utils.TestUtils.buildUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserTest {
    @Test
    void borrowBook_shouldAddBookToBorrowedList_whenThereIsNoBookBorrowed() {
        //given:
        User user = buildUser(UUID.randomUUID(), "Ron Weasly");
        Book book = mock(Book.class);

        //when:
        user.borrowBook(book);

        //then:
        verify(book).borrowBook();
        assertEquals(1, user.getBorrowedBookList().size());
        assertEquals(book, user.getBorrowedBookList().get(0));
    }

    @Test
    void borrowBook_shouldReturnExceededBorrowLimitException_whenUsersBorrowLimitExceeds() {
        //given:
        User user = buildUser(UUID.randomUUID(), "Ron Weasly", mock(Book.class), mock(Book.class));
        Book book = mock(Book.class);

        //when:
        assertThrows(ExceededBorrowedBookLimitException.class,() -> user.borrowBook(book));

        //then:
        verifyNoInteractions(book);
    }
}

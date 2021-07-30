package com.hexad.librarymanagement.user.domain;

import com.hexad.librarymanagement.book.domain.Book;
import com.hexad.librarymanagement.user.exception.AlreadyBorrowedSameBookException;
import com.hexad.librarymanagement.user.exception.ExceededBorrowedBookLimitException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.hexad.librarymanagement.utils.TestUtils.buildBook;
import static com.hexad.librarymanagement.utils.TestUtils.buildUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserTest {
    @Test
    void borrowBook_shouldAddBookToBorrowedList_whenThereIsNoBookBorrowed() {
        //given:
        User user = buildUser(UUID.randomUUID(), "Ron Weasley");
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
        User user = buildUser(UUID.randomUUID(), "Ron Weasley", mock(Book.class), mock(Book.class));
        Book book = mock(Book.class);

        //when:
        assertThrows(ExceededBorrowedBookLimitException.class,() -> user.borrowBook(book));

        //then:
        verifyNoInteractions(book);
    }


    @Test
    void borrowBook_shouldReturnAlreadyBorrowedSameBookException_whenUsersBorrowListContainsSameBook() {
        //given:
        Book lotr = buildBook(UUID.randomUUID(), "J.R.R. Tolkien", "Lord of the rings", "9780007141326", 7L);
        User user = buildUser(UUID.randomUUID(), "Ron Weasley", lotr);

        //when:
        assertThrows(AlreadyBorrowedSameBookException.class,() -> user.borrowBook(lotr));

    }
}

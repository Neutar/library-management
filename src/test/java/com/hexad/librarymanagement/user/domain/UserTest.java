package com.hexad.librarymanagement.user.domain;

import com.hexad.librarymanagement.book.domain.Book;
import com.hexad.librarymanagement.book.exception.BookNotBorrowedException;
import com.hexad.librarymanagement.user.exception.AlreadyBorrowedSameBookException;
import com.hexad.librarymanagement.user.exception.ExceededBorrowedBookLimitException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static com.hexad.librarymanagement.utils.TestUtils.buildBook;
import static com.hexad.librarymanagement.utils.TestUtils.buildUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserTest {
    @Test
    void borrowBook_shouldAddBookToBorrowedList_whenThereIsABookToBorrow() {
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
        Book book = mock(Book.class);
        User user = buildUser(UUID.randomUUID(), "Ron Weasley", book);
        when(book.getId()).thenReturn(UUID.randomUUID());

        //when:
        assertThrows(AlreadyBorrowedSameBookException.class,() -> user.borrowBook(book));
        
        //then:
        verify(book, times(2)).getId();
        verifyNoMoreInteractions(book);
    }
    
    @Test
    void returnBook_shouldRemoveTheBookFromBorrowedList_whenUserHasABookToReturn(){
        //given:
        Book book = mock(Book.class);
        User user = buildUser(UUID.randomUUID(), "Professor Albus Dumbledore", book);
        when(book.getId()).thenReturn(UUID.randomUUID());

        //when:
        user.removeBook(book);

        //then:
        assertEquals(0, user.getBorrowedBookList().size());
    }

    @Test
    public void returnBook_shouldThrowBookNotBorrowedException_whenTheReturnedBookIdListNotExistsInBorrowedList(){
        //given:
        Book book = mock(Book.class);
        User user = buildUser(UUID.randomUUID(), "Professor Albus Dumbledore");
        when(book.getId()).thenReturn(UUID.randomUUID());

        //when:
        assertThrows(BookNotBorrowedException.class, () -> user.removeBook(book));

        //then:
        verifyNoInteractions(book);

    }
}

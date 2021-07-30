package com.hexad.librarymanagement.user.service;

import com.hexad.librarymanagement.book.domain.Book;
import com.hexad.librarymanagement.book.exception.BookNotFoundException;
import com.hexad.librarymanagement.book.repository.BookRepository;
import com.hexad.librarymanagement.book.service.dto.BookDto;
import com.hexad.librarymanagement.user.domain.User;
import com.hexad.librarymanagement.user.exception.ExceededBorrowedBookLimitException;
import com.hexad.librarymanagement.user.exception.UserNotFoundException;
import com.hexad.librarymanagement.user.repository.UserRepository;
import com.hexad.librarymanagement.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.hexad.librarymanagement.utils.TestUtils.buildBook;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void borrowBook_shouldReturnUsersBorrowedList_whenThereIsABookToBorrow() {
        //given:
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        User user = mock(User.class);
        Book book = buildBook(UUID.randomUUID(), "Stephen King", "The Shining", "9783785746042");
        when(user.getBorrowedBookList()).thenReturn(Collections.singletonList(book));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        //when:
        List<BookDto> bookList = userService.borrowBook(userId, bookId);

        //then:
        assertNotNull(bookList);
        assertEquals(1, bookList.size());
        verify(user).borrowBook(book);
        verify(userRepository).save(user);
    }

    @Test
    public void borrowBook_shouldThrowBookNotFoundException_whenThereIsNoBookToBorrow() {
        //given:
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        User user = mock(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        //when:
        assertThrows(BookNotFoundException.class, () -> userService.borrowBook(userId, bookId));

        //then:
        verify(userRepository).findById(userId);
        verify(bookRepository).findById(bookId);
        verifyNoInteractions(user);
    }

    @Test
    public void borrowBook_shouldReturnExceededBorrowLimitException_whenUsersBorrowLimitExceeds(){
        //given:
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        Book book = buildBook(UUID.randomUUID(), "Stephen King", "The Shining", "9783785746042");
        User user = mock(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        doThrow(ExceededBorrowedBookLimitException.class).when(user).borrowBook(book);

        //when:
        assertThrows(ExceededBorrowedBookLimitException.class, () -> userService.borrowBook(userId, bookId));

        //then:
        verify(userRepository).findById(userId);
        verify(bookRepository).findById(bookId);

    }

    @Test
    public void borrowBook_shouldReturnNoSuchUserException_whenTheUserIsNotExists(){
        //given:
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when:
        assertThrows(UserNotFoundException.class,() -> userService.borrowBook(userId, bookId));

        //then:
        verify(userRepository).findById(userId);
        verifyNoInteractions(bookRepository);
    }
}

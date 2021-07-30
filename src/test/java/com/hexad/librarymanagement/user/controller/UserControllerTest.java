package com.hexad.librarymanagement.user.controller;


import com.hexad.librarymanagement.book.controller.response.BookResponse;
import com.hexad.librarymanagement.book.exception.BookNotFoundException;
import com.hexad.librarymanagement.book.service.dto.BookDto;
import com.hexad.librarymanagement.user.exception.ExceededBorrowedBookLimitException;
import com.hexad.librarymanagement.user.exception.UserNotFoundException;
import com.hexad.librarymanagement.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.hexad.librarymanagement.utils.TestUtils.buildBookDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void borrowBook_shouldReturnUsersBorrowedList_whenThereIsABookToBorrow() {
        //given:
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        BookDto bookDto = buildBookDto(bookId, "Stephen King", "The Shining", "9783785746042");
        when(userService.borrowBook(userId, bookId)).thenReturn(Collections.singletonList(bookDto));

        //when:
        List<BookResponse> bookResponseList = userController.borrowBook(userId, bookId);

        //then:
        verify(userService).borrowBook(bookId, userId);
        assertNotNull(bookResponseList);
        assertEquals(1, bookResponseList.size());
        assertThat(bookResponseList.get(0)).usingRecursiveComparison().isEqualTo(bookDto);
    }

    @Test
    void borrowBook_shouldThrowBookNotFoundException_whenThereIsNoBookToBorrow() {
        //given:
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        when(userService.borrowBook(userId, bookId)).thenThrow(BookNotFoundException.class);

        //when:
        assertThrows(BookNotFoundException.class, () -> userController.borrowBook(userId, bookId));

        //then:
        verify(userService).borrowBook(bookId, userId);
    }

    @Test
    void borrowBook_shouldReturnExceededBorrowLimitException_whenUsersBorrowLimitExceeds() {
        //given:
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        when(userService.borrowBook(userId, bookId)).thenThrow(ExceededBorrowedBookLimitException.class);

        //when:
        assertThrows(ExceededBorrowedBookLimitException.class, () -> userController.borrowBook(userId, bookId));

        //then:
        verify(userService).borrowBook(bookId, userId);
    }

    @Test
    void borrowBook_shouldReturnUserNotFoundException_whenTheUserIsNotExists() {
        //given:
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        when(userService.borrowBook(userId,bookId)).thenThrow(UserNotFoundException.class);

        //when:
        assertThrows(BookNotFoundException.class, () -> userController.borrowBook(userId, bookId));

        //then:
        verify(userService).borrowBook(bookId, userId);
    }
}

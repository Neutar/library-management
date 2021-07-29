package com.hexad.librarymanagement.book.service;

import com.hexad.librarymanagement.book.domain.Book;
import com.hexad.librarymanagement.book.repository.BookRepository;
import com.hexad.librarymanagement.book.service.dto.BookDto;
import com.hexad.librarymanagement.book.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.hexad.librarymanagement.utils.TestUtils.buildBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void getBooks_shouldReturnEmptyList_whenThereIsNoBook() {
        //given:
        when(bookRepository.findAllByBorrowedIsFalse()).thenReturn(Collections.emptyList());

        //when:
        List<BookDto> bookList = bookService.getAllBooks();

        //then:
        assertNotNull(bookList);
        assertEquals(0, bookList.size());
        verify(bookRepository).findAllByBorrowedIsFalse();
    }

    @Test
    void getBooks_shouldReturnListOfAllBooks_whenThereAreBooks() {
        //given:
        List<Book> bookList = Arrays.asList(buildBook("Stephen King", "The Shining", "9783785746042"),
                buildBook("J. K. Rowling", "Harry Potter Goblet of Fire", "9780439139595"));
        when(bookRepository.findAllByBorrowedIsFalse()).thenReturn(bookList);

        //when:
        List<BookDto> resultBookList = bookService.getAllBooks();

        //then:
        assertNotNull(resultBookList);
        assertEquals(2, resultBookList.size());
        assertThat(resultBookList).usingRecursiveFieldByFieldElementComparator().isEqualTo(bookList);

    }

}

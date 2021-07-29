package com.hexad.librarymanagement.service;

import com.hexad.librarymanagement.domain.Book;
import com.hexad.librarymanagement.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        //when:
        List<BookDto> bookList = bookService.getAllBooks();

        //then:
        assertNotNull(bookList);
        assertEquals(0, bookList.size());
        verify(bookRepository).findAll();
    }

    @Test
    void getBooks_shouldReturnListOfAllBooks_whenThereAreBooks() {
        //given:
        List<Book> bookList = Arrays.asList(buildBook("Stephen King", "The Shining", "9783785746042"),
                buildBook("J. K. Rowling", "Harry Potter Goblet of Fire", "9780439139595"));
        when(bookRepository.findAll()).thenReturn(bookList);

        //when:
        List<BookDto> resultBookList = bookService.getAllBooks();

        //then:
        assertNotNull(resultBookList);
        assertEquals(2, resultBookList.size());
        assertThat(resultBookList).usingRecursiveFieldByFieldElementComparator().isEqualTo(bookList);

    }

    private Book buildBook(String author, String bookName, String isbn) {
        return Book.builder().id(UUID.randomUUID()).author(author).name(bookName).isbn(isbn).build();
    }
}

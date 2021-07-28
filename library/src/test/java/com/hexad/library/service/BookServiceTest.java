package com.hexad.library.service;

import com.hexad.library.domain.Book;
import com.hexad.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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
        List<Book> bookList = bookService.getAllBooks();

        //then:
        assertNotNull(bookList);
        assertEquals(0, bookList.size());
        verify(bookRepository).findAll();
    }

    @Test
    void getBooks_shouldReturnListOfAllBooks_whenThereAreBooks() {
        //given:
        List<Book> bookList = Arrays.asList(buildBook("Stephen King", "The Shining", "lorem ipsum"),
                buildBook("J. K. Rowling", "Harry Potter Goblet of Fire", "lorem ipsum 2"));
        when(bookRepository.findAll()).thenReturn(bookList);

        //when:
        List<Book> resultBookList = bookService.getAllBooks();

        //then:
        assertNotNull(resultBookList);
        assertEquals(2, resultBookList.size());
        assertEquals(bookList, resultBookList);

    }

    private Book buildBook(String author, String bookName, String text) {
        return Book.builder().id(UUID.randomUUID()).author(author).name(bookName).text(text).build();
    }
}

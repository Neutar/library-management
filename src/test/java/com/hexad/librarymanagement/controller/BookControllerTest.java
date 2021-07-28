package com.hexad.librarymanagement.controller;

import com.hexad.librarymanagement.controller.response.BookResponse;
import com.hexad.librarymanagement.service.BookDto;
import com.hexad.librarymanagement.service.BookService;
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
class BookControllerTest {
    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    public void getBooks_shouldReturnEmptyList_whenThereIsNoBooks() {
        //given:
        when(bookService.getAllBooks()).thenReturn(Collections.emptyList());

        //when:
        List<BookResponse> bookResponseList = bookController.getAllBooksFromLibrary();

        //then:
        assertNotNull(bookResponseList);
        assertEquals(0, bookResponseList.size());
        verify(bookService).getAllBooks();
    }

    @Test
    public void getBooks_shouldReturnAllBookList_whenThereAreBooks() {
        //given:
        List<BookDto> bookDtoList = Arrays.asList(buildBookDto(UUID.randomUUID(), "Lord of the rings", "J.R.R. Tolkien", "9780007141326"),
                buildBookDto(UUID.randomUUID(), "War and peace", "Leo Tolstoy", "9783863523817"));
        when(bookService.getAllBooks()).thenReturn(bookDtoList);

        //when:
        List<BookResponse> resultBookList = bookController.getAllBooksFromLibrary();

        //then:
        assertNotNull(resultBookList);
        assertEquals(2, resultBookList.size());
        assertThat(resultBookList).usingRecursiveFieldByFieldElementComparator().isEqualTo(bookDtoList);
    }

    private BookDto buildBookDto(UUID id, String bookName, String author, String isbn) {
        return BookDto.builder().id(id).name(bookName).author(author).isbn(isbn).build();
    }

}

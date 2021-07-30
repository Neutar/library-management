package com.hexad.librarymanagement.book.controller;

import com.hexad.librarymanagement.common.IntegrationTestBase;
import com.hexad.librarymanagement.book.controller.response.BookResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookControllerIntegrationTest extends IntegrationTestBase {
    @Test
    void getBooks_shouldReturnEmptyArray_whenThereIsNoBook() {
        ResponseEntity<BookResponse[]> response =
                restTemplate.getForEntity(
                        "http://localhost:" + port + "/api/book",
                        BookResponse[].class);
        BookResponse[] books = response.getBody();
        assertEquals(0, books.length);
    }

    @Test
    @Sql(scripts={"/sql/add_lotr_book.sql",
            "/sql/add_borrowed_the_shining_book.sql"})
    void getBooks_shouldReturnBookList_whenThereAreBooks() {

        ResponseEntity<BookResponse[]> response =
                restTemplate.getForEntity(
                        "http://localhost:" + port + "/api/book",
                        BookResponse[].class);
        BookResponse[] books = response.getBody();
        assertNotNull(books);
        assertEquals(1, books.length);
        assertEquals("a69fb94a-9d87-4e8c-bd86-2576be316454", books[0].getId().toString());
        assertEquals("Lord of the rings", books[0].getName());
        assertEquals("J.R.R. Tolkien", books[0].getAuthor());
        assertEquals("9780007141326", books[0].getIsbn());
    }
}

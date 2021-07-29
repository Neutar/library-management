package com.hexad.librarymanagement.book.repository;

import com.hexad.librarymanagement.book.domain.Book;
import com.hexad.librarymanagement.common.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookRepositoryIntegrationTest extends IntegrationTestBase {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Sql(scripts = "/sql/add_lotr_book.sql")
    void findAllByBorrowedIsFalse_shouldReturn_whenThereIsBooksNotBorrowed() {
        List<Book> books = bookRepository.findAllByBorrowedIsFalse();

        assertEquals(1, books.size());
        assertEquals("a69fb94a-9d87-4e8c-bd86-2576be316454", books.get(0).getId().toString());
        assertEquals("Lord of the rings", books.get(0).getName());
        assertEquals("J.R.R. Tolkien", books.get(0).getAuthor());
        assertEquals("9780007141326", books.get(0).getIsbn());
    }
}

package com.hexad.librarymanagement.user.controller;

import com.hexad.librarymanagement.book.controller.response.BookResponse;
import com.hexad.librarymanagement.book.domain.Book;
import com.hexad.librarymanagement.book.repository.BookRepository;
import com.hexad.librarymanagement.common.IntegrationTestBase;
import com.hexad.librarymanagement.common.controller.response.ErrorResponse;
import com.hexad.librarymanagement.user.controller.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerIntegrationTest extends IntegrationTestBase {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Sql(scripts = {"/sql/add_ron_weasley_user.sql",
            "/sql/add_lotr_book.sql"})
    void borrowBook_shouldBorrow_whenBookIsAvailable() {
        //when:
        ResponseEntity<BookResponse[]> response =
                restTemplate.exchange(
                        "http://localhost:" + port + "/api/user/eb7c0d14-e2b8-4108-b0e7-d139ce53bd0e/book/a69fb94a-9d87-4e8c-bd86-2576be316454",
                        HttpMethod.PUT,
                        null,
                        BookResponse[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        BookResponse[] books = response.getBody();

        //then:
        assertNotNull(books);
        assertEquals(1, books.length);
        assertEquals("a69fb94a-9d87-4e8c-bd86-2576be316454", books[0].getId().toString());
        assertEquals("Lord of the rings", books[0].getName());
        assertEquals("J.R.R. Tolkien", books[0].getAuthor());
        assertEquals("9780007141326", books[0].getIsbn());

        //and:
        assertBookCopyCount(UUID.fromString("a69fb94a-9d87-4e8c-bd86-2576be316454"), 8L);
    }

    @Test
    void borrowBook_shouldReturnBadRequest_whenTheUserIsNotFound() {
        //when:
        ResponseEntity<ErrorResponse> response =
                restTemplate.exchange(
                        "http://localhost:" + port + "/api/user/eb7c0d14-e2b8-4108-b0e7-d139ce53bd0e/book/a69fb94a-9d87-4e8c-bd86-2576be316454",
                        HttpMethod.PUT,
                        null,
                        ErrorResponse.class);

        //then:
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertEquals("USER_NOT_FOUND", body.getMessage());
    }

    @Test
    @Sql(scripts = {"/sql/add_ron_weasley_user.sql"})
    void borrowBook_shouldReturnBadRequest_whenTheBookIsNotFound() {
        ResponseEntity<ErrorResponse> response =
                restTemplate.exchange(
                        "http://localhost:" + port + "/api/user/eb7c0d14-e2b8-4108-b0e7-d139ce53bd0e/book/a69fb94a-9d87-4e8c-bd86-2576be316454",
                        HttpMethod.PUT,
                        null,
                        ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertEquals("BOOK_NOT_FOUND", body.getMessage());
    }

    @Test
    @Sql(scripts = {"/sql/add_ron_weasley_user.sql",
            "/sql/add_lotr_book.sql",
            "/sql/add_borrowed_harry_potter_book.sql",
            "/sql/add_borrowed_the_shining_book.sql"})
    void borrowBook_shouldReturnBadRequest_whenUserAlreadyBorrowedTwoBook() {
        ResponseEntity<ErrorResponse> response =
                restTemplate.exchange(
                        "http://localhost:" + port + "/api/user/eb7c0d14-e2b8-4108-b0e7-d139ce53bd0e/book/a69fb94a-9d87-4e8c-bd86-2576be316454",
                        HttpMethod.PUT,
                        null,
                        ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertEquals("BORROWING_LIMIT_EXCEEDED", body.getMessage());
    }

    @Test
    @Sql(scripts = {"/sql/add_ron_weasley_user.sql",
            "/sql/add_lotr_book.sql",
            "/sql/add_borrowed_the_shining_book.sql"})
    void borrowBook_shouldReturnBadRequest_whenUserAlreadyBorrowedSameBook() {
        ResponseEntity<ErrorResponse> response =
                restTemplate.exchange(
                        "http://localhost:" + port + "/api/user/eb7c0d14-e2b8-4108-b0e7-d139ce53bd0e/book/23b62398-82b4-4d81-8704-343e8388fdab",
                        HttpMethod.PUT,
                        null,
                        ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertEquals("ALREADY_BORROWED_SAME_BOOK", body.getMessage());
    }

    @Test
    @Sql(scripts = {"/sql/add_ron_weasley_user.sql",
            "/sql/add_borrowed_harry_potter_book.sql"})
    void returnBook_shouldRemoveBookFromBorrowedBooks_whenBookWasBorrowed() {
        ResponseEntity<BookResponse[]> response =
                restTemplate.postForEntity(
                        "http://localhost:" + port + "/api/user/eb7c0d14-e2b8-4108-b0e7-d139ce53bd0e/book/return",
                        Collections.singleton("4ddf3d5e-9ed3-4c70-9438-31ed05404256"),
                        BookResponse[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        BookResponse[] books = response.getBody();

        assertNotNull(books);
        assertEquals(0, books.length);
        assertBookCopyCount(UUID.fromString("4ddf3d5e-9ed3-4c70-9438-31ed05404256"), 1L);
    }

    @Test
    @Sql(scripts = {"/sql/add_ron_weasley_user.sql",
            "/sql/add_borrowed_harry_potter_book.sql",
            "/sql/add_lotr_book.sql"})
    void returnBook_shouldReturnBookNotBorrowed_whenBookWasNotBorrowed() {
        ResponseEntity<ErrorResponse> response =
                restTemplate.postForEntity(
                        "http://localhost:" + port + "/api/user/eb7c0d14-e2b8-4108-b0e7-d139ce53bd0e/book/return",
                        Collections.singleton("a69fb94a-9d87-4e8c-bd86-2576be316454"),
                        ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();

        assertNotNull(errorResponse);
        assertEquals("BOOK_NOT_BORROWED", errorResponse.getMessage());
        assertBookCopyCount(UUID.fromString("4ddf3d5e-9ed3-4c70-9438-31ed05404256"), 0L);
        assertBookCopyCount(UUID.fromString("a69fb94a-9d87-4e8c-bd86-2576be316454"), 9L);
    }

    @Test
    void returnBook_shouldReturnBadRequest_whenTheUserIsNotFound() {
        ResponseEntity<ErrorResponse> response =
                restTemplate.postForEntity(
                        "http://localhost:" + port + "/api/user/eb7c0d14-e2b8-4108-b0e7-d139ce53bd0e/book/return",
                        Collections.singleton("4ddf3d5e-9ed3-4c70-9438-31ed05404256"),
                        ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertEquals("USER_NOT_FOUND", body.getMessage());
    }

    @Test
    @Sql(scripts = {"/sql/add_ron_weasley_user.sql"})
    void returnBook_shouldReturnBadRequest_whenTheBookIsNotFound() {
        // when:
        ResponseEntity<ErrorResponse> response =
                restTemplate.postForEntity(
                        "http://localhost:" + port + "/api/user/eb7c0d14-e2b8-4108-b0e7-d139ce53bd0e/book/return",
                        Collections.singleton("4ddf3d5e-9ed3-4c70-9438-31ed05404256"),
                        ErrorResponse.class);

        //then:
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertEquals("BOOK_NOT_FOUND", body.getMessage());
    }

    private void assertBookCopyCount(UUID bookId, Long count) {
        Optional<Book> book = bookRepository.findById(bookId);
        assertTrue(book.isPresent());
        assertEquals(count, book.get().getCopyCount());
    }

    @Test
    void getUsers_shouldReturnEmptyArray_whenThereIsNoUser() {
        ResponseEntity<UserResponse[]> response =
                restTemplate.getForEntity(
                        "http://localhost:" + port + "/api/user",
                        UserResponse[].class);
        UserResponse[] users = response.getBody();
        assertEquals(0, users.length);
    }

    @Test
    @Sql(scripts={"/sql/add_ron_weasley_user.sql",
            "/sql/add_borrowed_harry_potter_book.sql"})
    void getUsers_shouldReturnUserList_whenThereAreUsers() {

        ResponseEntity<UserResponse[]> response =
                restTemplate.getForEntity(
                        "http://localhost:" + port + "/api/user",
                        UserResponse[].class);
        UserResponse[] users = response.getBody();
        assertNotNull(users);
        assertEquals(1, users.length);
        assertEquals("eb7c0d14-e2b8-4108-b0e7-d139ce53bd0e", users[0].getId().toString());
        assertEquals("Ron Weasley", users[0].getName());
        assertEquals(1, users[0].getBorrowedBookList().size());
    }

}

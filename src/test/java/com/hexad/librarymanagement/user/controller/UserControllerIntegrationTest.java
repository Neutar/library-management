package com.hexad.librarymanagement.user.controller;

import com.hexad.librarymanagement.book.controller.response.BookResponse;
import com.hexad.librarymanagement.common.IntegrationTestBase;
import com.hexad.librarymanagement.common.controller.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserControllerIntegrationTest extends IntegrationTestBase {
    @Test
    @Sql(scripts={"/sql/add_ron_weasley_user.sql",
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
    }

    @Test
    void getBooks_shouldReturnBadRequest_whenTheUserIsNotFound() {
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
    @Sql(scripts={"/sql/add_ron_weasley_user.sql"})
    void getBooks_shouldReturnBadRequest_whenTheBookIsNotFound() {
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
    @Sql(scripts={"/sql/add_ron_weasley_user.sql",
            "/sql/add_lotr_book.sql",
            "/sql/add_borrowed_harry_potter_book.sql",
            "/sql/add_borrowed_the_shining_book.sql"})
    void getBooks_shouldReturnBadRequest_whenUserAlreadyBookTwoBook() {
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
}

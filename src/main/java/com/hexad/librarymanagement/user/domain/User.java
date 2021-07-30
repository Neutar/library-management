package com.hexad.librarymanagement.user.domain;

import com.hexad.librarymanagement.book.domain.Book;
import com.hexad.librarymanagement.user.exception.ExceededBorrowedBookLimitException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "LIBRARY_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private static final int BORROWING_LIMIT = 2;
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private List<Book> borrowedBookList = new ArrayList<>();

    public void borrowBook(Book book) {
        if (borrowedBookList.size() >= BORROWING_LIMIT) {
            throw new ExceededBorrowedBookLimitException();
        }
        borrowedBookList.add(book);
        book.borrowBook();
    }
}

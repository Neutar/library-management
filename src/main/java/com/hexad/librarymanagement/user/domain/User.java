package com.hexad.librarymanagement.user.domain;

import com.hexad.librarymanagement.book.domain.Book;
import com.hexad.librarymanagement.user.exception.AlreadyBorrowedSameBookException;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "LIBRARY_USER_BOOK",
            joinColumns = @JoinColumn(name = "library_user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> borrowedBookList = new ArrayList<>();

    public void borrowBook(Book book) {
        if (borrowedBookList.size() >= BORROWING_LIMIT) {
            throw new ExceededBorrowedBookLimitException();
        }
        if (borrowedBookList.stream().anyMatch(borrowed-> borrowed.getId().equals(book.getId()))) {
            throw new AlreadyBorrowedSameBookException();
        }
        borrowedBookList.add(book);
        book.borrowBook();
    }
}

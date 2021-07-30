package com.hexad.librarymanagement.book.domain;

import com.hexad.librarymanagement.book.exception.BookNotFoundException;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String author;
    private String isbn;
    private Long copyCount;

    public void borrowBook() {
        if(copyCount == 0) {
            throw new BookNotFoundException();
        }
        copyCount--;
    }

    public void returnBook() {
        copyCount++;
    }
}


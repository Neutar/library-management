package com.hexad.librarymanagement.book.domain;

import com.hexad.librarymanagement.book.exception.BookNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}


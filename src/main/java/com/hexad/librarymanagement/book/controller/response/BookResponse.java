package com.hexad.librarymanagement.book.controller.response;

import lombok.Data;

import java.util.UUID;

@Data
public class BookResponse {
    private UUID id;
    private String name;
    private String author;
    private String isbn;
}

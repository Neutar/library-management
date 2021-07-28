package com.hexad.library.service;

import lombok.Data;

import java.util.UUID;

@Data
public class BookDto {
    private UUID id;
    private String name;
    private String author;
    private String text;
}

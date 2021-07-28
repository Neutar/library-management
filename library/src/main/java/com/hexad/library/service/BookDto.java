package com.hexad.library.service;

import lombok.Data;
import lombok.Value;

import java.util.UUID;

@Value
public class BookDto {
    private UUID id;
    private String name;
    private String author;
    private String text;
}

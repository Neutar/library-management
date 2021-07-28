package com.hexad.library.service;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BookDto {
    private UUID id;
    private String name;
    private String author;
    private String text;
}

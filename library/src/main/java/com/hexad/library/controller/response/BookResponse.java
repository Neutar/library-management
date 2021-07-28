package com.hexad.library.controller.response;

import lombok.Data;

import java.util.UUID;

@Data
public class BookResponse {
    private UUID id;
    private String name;
    private String author;
    private String text;
}
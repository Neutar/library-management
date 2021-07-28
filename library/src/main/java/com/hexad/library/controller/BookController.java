package com.hexad.library.controller;

import com.hexad.library.controller.response.BookResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @GetMapping
    public List<BookResponse> getAllBooksFromLibrary() {
        return null;
    }
}

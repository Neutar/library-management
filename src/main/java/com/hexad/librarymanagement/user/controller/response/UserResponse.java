package com.hexad.librarymanagement.user.controller.response;

import com.hexad.librarymanagement.book.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String name;
    private List<Book> borrowedBookList = new ArrayList<>();
}

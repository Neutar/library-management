package com.hexad.librarymanagement.user.service.dto;

import com.hexad.librarymanagement.book.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String name;
    private List<Book> borrowedBookList = new ArrayList<>();
}

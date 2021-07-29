package com.hexad.librarymanagement.book.repository;

import com.hexad.librarymanagement.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}

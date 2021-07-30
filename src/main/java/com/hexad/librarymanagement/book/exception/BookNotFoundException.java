package com.hexad.librarymanagement.book.exception;

import com.hexad.librarymanagement.common.exception.NotFoundException;

public class BookNotFoundException extends NotFoundException {
    private static final String MESSAGE = "BOOK_NOT_FOUND";

    public BookNotFoundException() {
        super(MESSAGE);
    }
}

package com.hexad.librarymanagement.book.exception;

import com.hexad.librarymanagement.common.exception.LibraryManagementException;

public class BookNotBorrowedException extends LibraryManagementException {
    private static final String MESSAGE = "BOOK_NOT_BORROWED";
    public BookNotBorrowedException() {
        super(MESSAGE);
    }
}

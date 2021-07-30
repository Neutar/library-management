package com.hexad.librarymanagement.user.exception;

import com.hexad.librarymanagement.common.exception.LibraryManagementException;

public class AlreadyBorrowedSameBookException extends LibraryManagementException {
    private static final String MESSAGE = "ALREADY_BORROWED_SAME_BOOK";

    public AlreadyBorrowedSameBookException() {
        super(MESSAGE);
    }
}

package com.hexad.librarymanagement.user.exception;

import com.hexad.librarymanagement.common.exception.LibraryManagementException;

public class ExceededBorrowedBookLimitException extends LibraryManagementException {
    private static final String MESSAGE = "BORROWING_LIMIT_EXCEEDED";

    public ExceededBorrowedBookLimitException() {
        super(MESSAGE);
    }
}

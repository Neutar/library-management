package com.hexad.librarymanagement.user.exception;

import com.hexad.librarymanagement.common.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
        private static final String MESSAGE = "USER_NOT_FOUND";

        public UserNotFoundException() {
            super(MESSAGE);
        }
    }

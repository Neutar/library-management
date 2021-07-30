package com.hexad.librarymanagement.user.controller;

import com.hexad.librarymanagement.common.controller.response.ErrorResponse;
import com.hexad.librarymanagement.common.exception.LibraryManagementException;
import com.hexad.librarymanagement.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class RestResponseControllerAdvice {
  @ExceptionHandler
  protected ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException ex, WebRequest request) {
    String bodyOfResponse = ex.getMessage();
    log.error("Controller error", ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(bodyOfResponse));
  }

  @ExceptionHandler
  protected ResponseEntity<ErrorResponse> badRequestExceptionHandler(LibraryManagementException ex, WebRequest request) {
    String bodyOfResponse = ex.getMessage();
    log.error("Controller error", ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(bodyOfResponse));
  }
}

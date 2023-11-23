package com.ram.productApp.exceptionhandler;

import com.ram.productApp.exception.ProductNotAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotAvailableException.class)
    public ResponseEntity<Error> handleProduct(ProductNotAvailableException ex) {
        Error error = new Error( "Bad Request", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

package com.ram.productApp.exception;

public class ProductNotAvailableException extends RuntimeException{
    public ProductNotAvailableException(String message){
        super(message);
    }
}
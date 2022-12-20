package com.bootcamp.java.product.service.exception;

public class InvalidProductException extends Exception{
    private static final long serialVersionUID = 1L;
    public InvalidProductException(){super("This product is invalid");}
}

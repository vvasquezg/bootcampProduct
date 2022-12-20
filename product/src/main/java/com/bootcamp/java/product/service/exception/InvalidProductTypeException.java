package com.bootcamp.java.product.service.exception;

public class InvalidProductTypeException extends Exception{
    private static final long serialVersionUID = 1L;
    public InvalidProductTypeException(){super("This product type is invalid");}
}

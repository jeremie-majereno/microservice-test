package com.example.bank.customer;

public class CustomerException extends RuntimeException {

    private String msg;

    public CustomerException(String msg){
        super(msg);
    }
}

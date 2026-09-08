package com.example.bank.customer;

public record CustomerRequest(
        String firstname,
        String lastname,
        String email,
        String status,
        String password
) {

}

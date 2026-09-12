package com.example.bank.customer;

public record CustomerResponse(
        Long customerId,
        String firstname,
        String lastname,
        String email


) {
}

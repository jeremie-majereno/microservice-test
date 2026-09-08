package com.example.bank.customer;

public record CustomerResponse(
        Long id,
        String firstname,
        String lastname,
        String email
) {
}

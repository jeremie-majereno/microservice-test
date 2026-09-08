package com.example.bank.account;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public record AccountRequest(


        String accountNumber,
        Long customerId,
        BigDecimal balance,

        @Enumerated(EnumType.STRING)
                Type type,
        @Enumerated(EnumType.STRING)
                Status status
) {

}

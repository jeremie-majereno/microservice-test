package com.example.bank.transaction;

import java.math.BigDecimal;

public record DepositResponse(
        BigDecimal amount,
        String etat
) {
}

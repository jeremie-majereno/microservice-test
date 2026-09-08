package com.example.bank.transaction;

import java.math.BigDecimal;

public record DepositRequest(
        Long accountId,
        BigDecimal amount
) {
}

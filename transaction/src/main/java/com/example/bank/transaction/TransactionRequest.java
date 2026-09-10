package com.example.bank.transaction;

import java.math.BigDecimal;

public record TransactionRequest(
        Long accountId,
        String transferId,
        TransactionType type,
        BigDecimal amount,
        BigDecimal balanceAfter
) {
}

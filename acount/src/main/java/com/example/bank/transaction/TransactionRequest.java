package com.example.bank.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequest(
        Long accountId,
        String transferId,
        TransactionType type,
        BigDecimal amount,
        BigDecimal balanceAfter,
        LocalDateTime createAt
) {
}

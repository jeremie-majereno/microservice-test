package com.example.bank.transaction;

import java.math.BigDecimal;

public record TransferResponse(
        String transferId,
        Long fromAccountId,
        Long toAccountId,
        BigDecimal amount,
        BigDecimal senderBalanceAfter,
        BigDecimal receiverBalanceAfter,
        String status
) {
}

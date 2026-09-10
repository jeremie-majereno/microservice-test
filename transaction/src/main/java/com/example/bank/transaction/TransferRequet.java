package com.example.bank.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferRequet(
        Long fromAccountId,
        Long toAccountId,
        BigDecimal amount,
        String transferId,
        BigDecimal senderNewBalance,
        BigDecimal receiverNewBalance,
        LocalDateTime createAt
) {
}

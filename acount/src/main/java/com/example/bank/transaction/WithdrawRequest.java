package com.example.bank.transaction;

import java.math.BigDecimal;

public record WithdrawRequest(
        Long accountId,
        BigDecimal amount
) {
}

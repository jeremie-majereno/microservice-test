package com.example.bank.transaction;

import java.math.BigDecimal;

public record WithdrawResponse(
        BigDecimal amount,
        String status
) {
}

package com.example.bank.transaction;

import com.example.bank.account.Status;
import com.example.bank.account.Type;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public record TransferRequest(

        Long fromAccountId,
        Long toAccountId,
        BigDecimal amount
) {
}

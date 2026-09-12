package com.example.bank.account;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public record AccountRequest(
        Long customerId

) {

}

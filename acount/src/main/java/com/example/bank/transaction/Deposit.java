package com.example.bank.transaction;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Deposit {

    @Id
    @GeneratedValue
    private Long id;

    private Long accountId;
    private BigDecimal amount;
    private boolean success;
}

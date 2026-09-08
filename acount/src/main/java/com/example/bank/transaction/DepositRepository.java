package com.example.bank.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository  extends JpaRepository<Deposit,Long> {
}

package com.example.bank.account;

import com.example.bank.customer.CustomerResponse;
import com.example.bank.transaction.Deposit;
import com.example.bank.transaction.DepositRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountMapper {

    public Account toAccount (AccountRequest request){
        return Account.builder()
                .balance(BigDecimal.ZERO)
                .customerId(request.customerId())
                .status(Status.ADMIN)
                .type(Type.VISA)
                .build();
    }

    public AccountResponse fromAccount(Account account, CustomerResponse customerResponse){
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                customerResponse
        );
    }

    public Deposit toDeposit(DepositRequest request){
        return Deposit.builder()
                .accountId(request.accountId())
                .amount(request.amount())
                .build() ;
    }

}

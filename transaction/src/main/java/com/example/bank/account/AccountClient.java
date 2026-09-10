package com.example.bank.account;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "account-transaction")
public interface AccountClient {

    @GetMapping("/api/v1/accounts")
    Optional<AccountResponse> findAccountById(@PathVariable("id") Long id);
}

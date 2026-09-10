package com.example.bank.transaction;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "transaction-service")
public interface TransactionClient {

    @PostMapping("/api/v1/transactions/transfer")
    void toSaveTransfer(TransferResponse request);
}

package com.example.bank.transaction;


import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "transaction-service")
public interface TransactionClient {

    @PostMapping("/api/v1/transactions/transfer")
    void toSaveTransfer(@Valid @RequestBody TransferResponse request);

    @PostMapping("/api/v1/transactions")
    void transactionSave(@Valid @RequestBody TransactionRequest request);
}

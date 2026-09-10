package com.example.bank.transaction;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionResponse> create(
            @RequestBody TransactionRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> findByAccountId(
            @PathVariable Long accountId
    ) {
        return ResponseEntity.ok(
                service.findByAccountId(accountId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                service.findById(id)
        );
    }

    @PostMapping("/api/v1/transactions/transfer")
    public ResponseEntity<TransactionResponse> doTransf (@Valid @RequestBody TransferRequet requet){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.doTransfer(requet));
    }
}
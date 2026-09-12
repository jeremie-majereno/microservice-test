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
    public ResponseEntity<Void> create(
          @Valid  @RequestBody TransactionRequest request
    ) {

        service.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
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

    @PostMapping("/transfer")
    public ResponseEntity<Void> doTransf (@Valid @RequestBody TransferRequet requet){
        service.doTransfer(requet);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
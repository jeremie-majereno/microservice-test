package com.example.bank.transaction;

import jakarta.persistence.GeneratedValue;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

import static com.example.bank.transaction.TransactionType.TRANSFER_IN;
import static com.example.bank.transaction.TransactionType.TRANSFER_OUT;

@Service
public class TransactionMapper {

    public Transaction toTransaction(TransactionRequest request){
        return Transaction.builder()
                .accountId(request.accountId())
                .transferId(request.transferId())
                .type(request.type())
                .amount(request.amount())
                .balanceAfter(request.balanceAfter())
                .createdAt(request.createAt())
                .build();
    }

    public TransactionResponse fromTransaction(Transaction transaction){
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAccountId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBalanceAfter(),
                transaction.getCreatedAt()
        );
    }

    public Transaction toSenderTransfer (TransferRequet requet){
        return Transaction.builder()
                .accountId(requet.fromAccountId())
                .transferId(requet.transferId())
                .type(TRANSFER_OUT)
                .amount(requet.amount())
                .createdAt(requet.createAt())
                .balanceAfter(requet.senderNewBalance())
                .build();
    }


    public Transaction toReceiverTransfer (TransferRequet requet){
        return Transaction.builder()
                .accountId(requet.toAccountId())
                .transferId(requet.transferId())
                .type(TRANSFER_IN)
                .amount(requet.amount())
                .createdAt(requet.createAt())
                .balanceAfter(requet.receiverNewBalance())
                .build();
    }


}

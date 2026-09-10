package com.example.bank.transaction;

import com.example.bank.account.AccountClient;
import com.example.bank.account.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {


    private final TransactionRepository repository;
    private final TransactionMapper mapper;


    public TransactionResponse create(TransactionRequest request) {
        validation(request.amount());

        Transaction transaction = mapper.toTransaction(request);

        Transaction saveTransaction = repository.save(transaction);


        return mapper.fromTransaction(saveTransaction);
    }

    public List<TransactionResponse> findByAccountId(Long accountId) {
        return repository.findByAccountIdOrderByCreatedAtDesc(accountId)
                .stream()
                .map(mapper::fromTransaction)
                .collect(Collectors.toList());
    }

    public TransactionResponse findById(Long id) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Transaction  not found"));

        return mapper.fromTransaction(transaction);
    }

    public void validation(BigDecimal amount){
        if(amount== null){
            throw new RuntimeException("amount cannot be null");
        }

        if(amount.compareTo(BigDecimal.ZERO)>0){
            throw new RuntimeException("amount should be positive");
        }
    }

    public TransactionResponse doTransfer(TransferRequet requet){

        validation(requet.amount());

        Transaction senderTrans = mapper.toSenderTransfer(requet);
        Transaction receiverTrans = mapper.toReceiverTransfer(requet);

        var responser  = repository.save(senderTrans);
        repository.save(receiverTrans);
        return mapper.fromTransaction(responser);
    }
}

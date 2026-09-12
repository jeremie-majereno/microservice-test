package com.example.bank.account;

import com.example.bank.customer.CustomerClient;
import com.example.bank.customer.CustomerResponse;
import com.example.bank.transaction.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerClient customerClient;
    private final TransactionClient transactionClient;

    @Transactional
    public AccountResponse create(AccountRequest request) {

        //Vérifier que le customer existe
        CustomerResponse customer = customerClient
                .findCustomerById(request.customerId())
                .orElseThrow(() ->
                        new AccountException("Customer not found: " + request.customerId())
                );

        //to check if the customer have another account
        var check = accountRepository.existsByCustomerId(request.customerId());


        //Créer le compte
        Account account = accountMapper.toAccount(request);


        //Initialiser le solde
        if (account.getBalance() == null || account.getBalance() != null) {
            account.setBalance(BigDecimal.ZERO);
        }
        account.setAccountNumber(UUID.randomUUID().toString());

        Account savedAccount = accountRepository.save(account);
        return accountMapper.fromAccount(savedAccount, customer);
    }


    public AccountResponse findAccountById(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountException("Account not found: " + accountId)
                );

        CustomerResponse customer = customerClient
                .findCustomerById(account.getCustomerId())
                .orElseThrow(() ->
                        new AccountException(
                                "Customer not found: " + account.getCustomerId()
                        )
                );

        return accountMapper.fromAccount(account, customer);
    }


    public CustomerResponse findCustomerById(Long customerId) {

        return customerClient.findCustomerById(customerId)
                .orElseThrow(() ->
                        new AccountException("Customer not found: " + customerId)
                );
    }


    @Transactional
    public DepositResponse deposit(DepositRequest request) {

        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() ->
                        new AccountException("Account not found: " + request.accountId())
                );

        BigDecimal amount = request.amount();

        // Vérifier le montant
        validateAmount(amount);

        // Ajouter l'argent
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        var saveAccount = accountRepository.save(account);

        transactionClient.transactionSave(new TransactionRequest(
              saveAccount.getId(),
              "--",
              TransactionType.DEPOSIT,
              amount,
              newBalance,
              LocalDateTime.now()
        ));

        return new DepositResponse(
                amount,
                "SUCCESS"
        );
    }


    @Transactional
    public DepositResponse withdraw(DepositRequest request) {

        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() ->
                        new AccountException(
                                "Account not found: " + request.accountId()
                        )
                );

        BigDecimal amount = request.amount();

        // Vérifier le montant
        validateAmount(amount);
        BigDecimal currentBalance = account.getBalance();

        if (currentBalance.compareTo(amount) <= 0) {
            throw new AccountException(
                    "Insufficient balance. Current balance: " + currentBalance
            );
        }

        BigDecimal newBalance = currentBalance.subtract(amount);
        account.setBalance(newBalance);
        var saveAccount = accountRepository.save(account);

        transactionClient.transactionSave(new TransactionRequest(
                saveAccount.getId(),
                "--",
                TransactionType.WITHDRAW,
                amount,
                newBalance,
                LocalDateTime.now()
        ));

        return new DepositResponse(
                amount,
                "SUCCESS"
        );
    }

    @Transactional
    public AccountResponse transfer(TransferRequest request) {


        //check the amount
        validateAmount(request.amount());

        //to find the account bord
        var sender = accountRepository.findById(request.fromAccountId())
                .orElseThrow(() ->
                        new AccountException("Sender account not found"));
        var receiver = accountRepository.findById(request.toAccountId())
                .orElseThrow(() ->
                        new AccountException("receiver account not found"));

        //to check if it's the same account
        if (sender.getId().equals(receiver.getId())) {
            throw new AccountException(
                    "Sender and receiver accounts must be different"
            );
        }


        //check if the sender can send this amount
        BigDecimal senderAmount = sender.getBalance();
        BigDecimal amount = request.amount();
        if (senderAmount.compareTo(amount) <= 0) {
            throw new AccountException(
                    "Insufficient balance. Current balance : "
            );
        }

        //set the balance of the account
        BigDecimal senderNewBalance = senderAmount.subtract(amount);
        sender.setBalance(senderNewBalance);

        BigDecimal receiverNewBalance = receiver.getBalance().add(amount);
        receiver.setBalance(receiverNewBalance);

        //save
        var senderSave = accountRepository.save(sender);
        accountRepository.save(receiver);

        //send to transaction for save it
        String transferId = UUID.randomUUID().toString();
        transactionClient.toSaveTransfer(new TransferResponse(
                request.fromAccountId(),
                request.toAccountId(),
                amount,
                transferId,
                senderNewBalance,
                receiverNewBalance,
                LocalDateTime.now()
        ));

        //response
        var senderCustomer = customerClient.findCustomerById(request.fromAccountId()).orElseThrow();
        return accountMapper.fromAccount(senderSave, senderCustomer);

    }


    private void validateAmount(BigDecimal amount) {

        if (amount == null) {
            throw new AccountException("Amount cannot be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AccountException(
                    "Amount must be greater than zero"
            );
        }
    }
}
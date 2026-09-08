package com.example.bank.account;

import com.example.bank.customer.CustomerClient;
import com.example.bank.customer.CustomerResponse;
import com.example.bank.transaction.DepositRequest;
import com.example.bank.transaction.DepositResponse;
import com.example.bank.transaction.WithdrawRequest;
import com.example.bank.transaction.WithdrawResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerClient customerClient;

    @Transactional
    public AccountResponse create(AccountRequest request) {

        // Vérifier que le customer existe
        CustomerResponse customer = customerClient
                .findCustomerById(request.customerId())
                .orElseThrow(() ->
                        new AccountException("Customer not found: " + request.customerId())
                );

        // Créer le compte
        Account account = accountMapper.toAccount(request);

        // Initialiser le solde
        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }

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
    public DepositResponse deposit(Long accountId, DepositRequest request) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountException("Account not found: " + accountId)
                );

        BigDecimal amount = request.amount();

        // Vérifier le montant
        validateAmount(amount);

        // Ajouter l'argent
        BigDecimal newBalance = account.getBalance().add(amount);

        account.setBalance(newBalance);

        accountRepository.save(account);

        return new DepositResponse(
                amount,
                "SUCCESS"
        );
    }


    @Transactional
    public WithdrawResponse withdraw(WithdrawRequest request) {

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

        // Vérifier le solde
        if (currentBalance.compareTo(amount) < 0) {
            throw new AccountException(
                    "Insufficient balance. Current balance: " + currentBalance
            );
        }

        // Retirer l'argent
        BigDecimal newBalance = currentBalance.subtract(amount);

        account.setBalance(newBalance);

        accountRepository.save(account);

        return new WithdrawResponse(
                amount,
                "SUCCESS"
        );
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
package com.example.bank.account;


import com.example.bank.customer.CustomerResponse;
import com.example.bank.transaction.DepositRequest;
import com.example.bank.transaction.DepositResponse;
import com.example.bank.transaction.WithdrawRequest;
import com.example.bank.transaction.WithdrawResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount (AccountRequest request){
        return ResponseEntity.ok(accountService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById (@PathVariable("lid") Long accountId){
        return ResponseEntity.ok(accountService.findAccountById(accountId));
    }

    @GetMapping("/find_customer/{id}")
    public ResponseEntity<CustomerResponse> findCustomerById (@PathVariable("id") Long customerId){
        return ResponseEntity.ok(accountService.findCustomerById(customerId));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<DepositResponse> deposit(@PathVariable("id") Long id, DepositRequest request){
        return ResponseEntity.ok(accountService.deposit(id,request));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<WithdrawResponse> withdraw(@PathVariable("id")  WithdrawRequest request){
        return ResponseEntity.ok(accountService.withdraw(request));
    }

}

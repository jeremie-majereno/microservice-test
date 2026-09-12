package com.example.bank.account;


import com.example.bank.customer.CustomerResponse;
import com.example.bank.transaction.DepositRequest;
import com.example.bank.transaction.DepositResponse;
import com.example.bank.transaction.TransferRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private  final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount (@Valid @RequestBody AccountRequest request){
        return ResponseEntity.ok(accountService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById (@PathVariable("id") Long accountId){
        return ResponseEntity.ok(accountService.findAccountById(accountId));
    }

    @GetMapping("/find_customer/{id}")
    public ResponseEntity<CustomerResponse> findCustomerById (@PathVariable("id") Long customerId){
        return ResponseEntity.ok(accountService.findCustomerById(customerId));
    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponse> deposit(@RequestBody DepositRequest request){
        return ResponseEntity.ok(accountService.deposit(request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<DepositResponse> withdraw(@RequestBody  DepositRequest request){
        return ResponseEntity.ok(accountService.withdraw(request));
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(
          @Valid  @RequestBody TransferRequest request){
        return ResponseEntity.ok(accountService.transfer(request));
    }
}

package com.example.bank.customer;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class ControllerCustomer {

    private final ServiceCostomer serviceCostomer;

    @PostMapping
    public ResponseEntity<CustomerResponse> create (@Valid @RequestBody  CustomerRequest request){
        return ResponseEntity.ok(serviceCostomer.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remode(@PathVariable("id") Long id){
        serviceCostomer.remove(id);
        return ResponseEntity.accepted().build();
    }


    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable("customer-id") Long id){
        return ResponseEntity.ok(serviceCostomer.findCustomerId(id));
    }

    @GetMapping("/find-customers")
    public ResponseEntity<List<CustomerResponse>> findAll(){
        return ResponseEntity.ok(serviceCostomer.findAll());
    }

}

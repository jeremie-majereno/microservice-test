package com.example.bank.customer;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RestControllerAdvice
@RequestMapping("/api/v1/customer")
public class ControllerCustomer {

    private ServiceCostomer serviceCostomer;

    @PostMapping
    public ResponseEntity<CustomerReponse> create (@Valid @RequestBody  CustomerRequest request){
        return ResponseEntity.ok(serviceCostomer.create(request));
    }

    @DeleteMapping()
    public ResponseEntity<Void> remode(Long id){
        serviceCostomer.remove(id);
        return ResponseEntity.accepted().build();
    }


    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerReponse> findById(@Valid @RequestBody Long id){
        return ResponseEntity.ok(serviceCostomer.findCustomerId(id));
    }

    @GetMapping("/find-customers")
    public ResponseEntity<List<CustomerReponse>> findAll(){
        return ResponseEntity.ok(serviceCostomer.findAll());
    }

}

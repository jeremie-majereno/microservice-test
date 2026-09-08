package com.example.bank.customer;


import jakarta.persistence.GeneratedValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "customer-service")
public interface CustomerClient {

    @GetMapping("/api/v1/customers/{customer-id}")
    Optional<CustomerResponse> findCustomerById(@PathVariable("customer-id") Long customerId) ;
}

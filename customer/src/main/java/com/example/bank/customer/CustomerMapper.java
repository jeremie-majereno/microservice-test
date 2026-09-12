package com.example.bank.customer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMapper {

    public CustomerResponse fromCustomer(Customer customer){
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail()

        );
    }

    public Customer  toCustomer ( CustomerRequest request){
        return Customer.builder()
                .email(request.email())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .build();
    }



    public List<CustomerResponse> findAll(List<Customer> customers){

        if(customers.isEmpty()) return null;

        List<CustomerResponse> reponses = customers.stream()
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getFirstname(),
                        customer.getLastname(),
                        customer.getEmail()
                )).collect(Collectors.toList());

        return reponses;
    }



}

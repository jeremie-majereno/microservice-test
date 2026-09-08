package com.example.bank.customer;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMapper {

    public CustomerReponse fromCustomer(Customer customer){
        return new CustomerReponse(
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getStatus()
        );
    }

    public Customer  toCustomer ( CustomerRequest request){
        return Customer.builder()
                .email(request.email())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .password(request.password())
                .status(request.status())
                .build();
    }



    public List<CustomerReponse> findAll(List<Customer> customers){

        if(customers.isEmpty()) return null;

        List<CustomerReponse> reponses = customers.stream()
                .map(customer -> new CustomerReponse(
                        customer.getFirstname(),
                        customer.getLastname(),
                        customer.getEmail(),
                        customer.getStatus()
                )).collect(Collectors.toList());

        return reponses;
    }



}

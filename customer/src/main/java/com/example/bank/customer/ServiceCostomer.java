package com.example.bank.customer;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Service
@RestControllerAdvice
public class ServiceCostomer {


    private RepositoryCustomer repositoryCustomer;
    private CustomerMapper mapper;

    public CustomerReponse create(CustomerRequest request) {
        var check = repositoryCustomer
                .findByEmail(request.email())
                .isPresent();

        if(check){
            throw new CustomerException("this customer is already exits");
        }

        var customer = repositoryCustomer.save(mapper.toCustomer(request));
        var response = mapper.fromCustomer(customer);
        return response;
    }

    public void remove(Long id) {
        repositoryCustomer.deleteById(id);
    }

    public CustomerReponse findCustomerId( Long id) {

        var customer = repositoryCustomer.findById(id)
                .map(mapper::fromCustomer)
                .orElseThrow(()->new CustomerException("customer not find"));

        return customer;
    }

    public List<CustomerReponse> findAll() {
        var customers = repositoryCustomer.findAll();
        return mapper.findAll(customers);
    }
}

package com.example.bank.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceCostomer {


    private  final RepositoryCustomer repositoryCustomer;
    private  final CustomerMapper mapper;

    public CustomerResponse create(CustomerRequest request) {
        var check = repositoryCustomer
                .findByEmail(request.email())
                .isPresent();

        if(check){
            throw new CustomerException("this customer is already exits");
        }

        var customer = repositoryCustomer.save(mapper.toCustomer(request));
        return mapper.fromCustomer(customer);
    }

    public void remove(Long id) {
        repositoryCustomer.deleteById(id);
    }

    public CustomerResponse findCustomerId(Long id) {
        var customer = repositoryCustomer.findById(id)
                .map(mapper::fromCustomer)
                .orElseThrow(()->new CustomerException("customer not find"));

        return customer;
    }

    public List<CustomerResponse> findAll() {
        var customers = repositoryCustomer.findAll();
        return mapper.findAll(customers);
    }
}

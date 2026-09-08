package com.example.bank.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryCustomer extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}

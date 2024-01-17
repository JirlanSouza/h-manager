package com.js.hmanager.booking.customer.domain;

import com.js.hmanager.booking.customer.domain.Cpf;

import java.util.UUID;

public interface CustomerRepository {
    boolean exists(UUID id);

    boolean existsByCpf(Cpf cpf);

    void save(Customer customer);
}

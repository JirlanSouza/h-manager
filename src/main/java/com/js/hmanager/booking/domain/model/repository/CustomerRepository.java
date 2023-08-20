package com.js.hmanager.booking.domain.model.repository;

import com.js.hmanager.booking.domain.model.entity.Cpf;
import com.js.hmanager.booking.domain.model.entity.Customer;

import java.util.UUID;

public interface CustomerRepository {
    boolean exists(UUID id);

    boolean existsByCpf(Cpf cpf);

    void save(Customer customer);
}

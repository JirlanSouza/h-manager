package com.js.hmanager.reservation.customer.domain;

import java.util.UUID;

public interface CustomerRepository {
    boolean exists(UUID id);

    boolean existsByCpf(Cpf cpf);

    void save(Customer customer);
}

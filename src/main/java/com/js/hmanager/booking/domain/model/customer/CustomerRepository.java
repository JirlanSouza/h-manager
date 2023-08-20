package com.js.hmanager.booking.domain.model.customer;

import java.util.UUID;

public interface CustomerRepository {
    boolean exists(UUID id);

    boolean existsByCpf(Cpf cpf);

    void save(Customer customer);
}

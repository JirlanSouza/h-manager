package com.js.hmanager.booking.domain.model.customer;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public class Customer {
    private final UUID id;
    private String name;
    private Cpf cpf;
    private String email;
    private String telephone;
    private Address address;
    private OffsetDateTime customerSince;

    public Customer(String name, Cpf cpf, String email, String telephone, Address address) {
        this(UUID.randomUUID(), name, cpf, email, telephone, address, OffsetDateTime.now());
    }

    private Customer(UUID id, String name, Cpf cpf, String email, String telephone, Address address, OffsetDateTime customerSince) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.customerSince = customerSince;
    }
}

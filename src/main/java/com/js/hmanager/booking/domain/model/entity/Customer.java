package com.js.hmanager.booking.domain.model.entity;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Customer {
    private final UUID id;
    private String name;
    private Cpf cpf;
    private Address address;

    public Customer(String name, Cpf cpf, Address address) {
        this(UUID.randomUUID(), name, cpf, address);
    }

    private Customer(UUID id, String name, Cpf cpf, Address address) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.address = address;
    }
}

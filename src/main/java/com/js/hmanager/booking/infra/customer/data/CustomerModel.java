package com.js.hmanager.booking.infra.customer.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class CustomerModel {
    @Id
    private UUID id;
    private String name;
    private String cpf;
    private String addressStreet;
    private String addressNumber;
    private String addressNeighborhood;
    private String addressZipCode;
    private String addressCity;
    private String addressState;
    private String addressCountry;
}

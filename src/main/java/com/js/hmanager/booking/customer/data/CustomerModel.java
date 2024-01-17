package com.js.hmanager.booking.customer.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class CustomerModel {
    @Id
    private UUID id;
    private String name;
    private String cpf;
    private String email;
    private String telephone;
    private String addressStreet;
    private String addressNumber;
    private String addressNeighborhood;
    private String addressZipCode;
    private String addressCity;
    private String addressState;
    private String addressCountry;
    private OffsetDateTime customerSince;
}

package com.js.hmanager.booking.customer.application;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class CustomerSummary {
    @Id
    UUID id;
    String name;
    String cpf;
    String email;
    OffsetDateTime customerSince;
}

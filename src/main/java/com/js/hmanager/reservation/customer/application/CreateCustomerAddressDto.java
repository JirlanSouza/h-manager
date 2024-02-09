package com.js.hmanager.reservation.customer.application;

import com.js.hmanager.reservation.customer.domain.Address;

public record CreateCustomerAddressDto(
        String street,
        String houseNumber,
        String neighborhood,
        String zipCode,
        String city,
        String state,
        String country
) {
    public Address toDomainAddress() {
        return new Address(street, houseNumber, neighborhood, zipCode, city, state, country);
    }
}

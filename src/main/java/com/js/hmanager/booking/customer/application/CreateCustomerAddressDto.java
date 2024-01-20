package com.js.hmanager.booking.customer.application;

import com.js.hmanager.booking.customer.domain.Address;

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
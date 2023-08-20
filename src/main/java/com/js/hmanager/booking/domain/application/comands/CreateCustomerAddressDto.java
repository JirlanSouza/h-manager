package com.js.hmanager.booking.domain.application.comands;

import com.js.hmanager.booking.domain.model.customer.Address;

public record CreateCustomerAddressDto(
        String street,
        String number,
        String neighborhood,
        String zipCode,
        String city,
        String state,
        String country
) {
    public Address toDomainAddress() {
        return new Address(street, number, neighborhood, zipCode, city, state, country);
    }
}

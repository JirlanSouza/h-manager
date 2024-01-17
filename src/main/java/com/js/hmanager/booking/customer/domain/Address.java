package com.js.hmanager.booking.customer.domain;

public record Address(
        String street,
        String houseNumber,
        String neighborhood,
        String zipCode,
        String City,
        String state,
        String country
) {
}

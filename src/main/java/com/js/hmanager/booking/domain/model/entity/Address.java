package com.js.hmanager.booking.domain.model.entity;

public record Address(
        String street,
        String number,
        String neighborhood,
        String zipCode,
        String City,
        String state,
        String country
) {
}

package com.js.hmanager.booking.customer.application;

import com.js.hmanager.booking.customer.application.CreateCustomerAddressDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCustomerDto(
        @NotBlank
        @Size(min = 3, max = 70)
        String name,
        @NotNull
        @Size(min = 11, max = 14)
        String cpf,

        @Size(min = 10, max = 100)
        String email,

        @NotNull()
        @Size(min = 11, max = 15)
        String telephone,
        @NotNull
        CreateCustomerAddressDto address
) {
}
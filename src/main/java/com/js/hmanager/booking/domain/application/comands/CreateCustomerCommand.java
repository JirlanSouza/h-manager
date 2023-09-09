package com.js.hmanager.booking.domain.application.comands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCustomerCommand(
        @NotBlank
        String name,
        @NotNull
        String cpf,

        String email,

        String telephone,
        @NotNull
        CreateCustomerAddressDto address
) {
}
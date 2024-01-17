package com.js.hmanager.inventory.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateRoomDto(
        @NotBlank
        String number,

        @NotNull
        int doubleBeds,

        @NotNull
        int singleBeds,

        @NotNull
        BigDecimal dailyRate,

        @NotNull
        boolean available
) {
}

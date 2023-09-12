package com.js.hmanager.inventory.domain.application.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateRoomCommand(
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

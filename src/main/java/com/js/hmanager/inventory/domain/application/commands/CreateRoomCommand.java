package com.js.hmanager.inventory.domain.application.commands;

import java.math.BigDecimal;

public record CreateRoomCommand(String number, int doubleBeds, int singleBeds, BigDecimal dailyTax, boolean available) {
}

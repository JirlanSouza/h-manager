package com.js.hmanager.booking.domain.model.booking;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Room {
    private final UUID id;
    private String number;
    private BigDecimal dailyTax;

    public Room(String number, BigDecimal dailyTax) {
        this.id = UUID.randomUUID();
        this.number = number;
        this.dailyTax = dailyTax;
    }
}

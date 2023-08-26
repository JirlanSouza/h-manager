package com.js.hmanager.booking.domain.model.booking;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Room {
    private final UUID id;
    private String number;
    private BigDecimal dailyRate;

    public Room(String number, BigDecimal dailyRate) {
        this.id = UUID.randomUUID();
        this.number = number;
        this.dailyRate = dailyRate;
    }
}

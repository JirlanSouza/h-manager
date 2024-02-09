package com.js.hmanager.reservation.reservation.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingRoom {
    private final UUID id;
    private String number;
    private BigDecimal dailyRate;

    public BookingRoom(String number, BigDecimal dailyRate) {
        this.id = UUID.randomUUID();
        this.number = number;
        this.dailyRate = dailyRate;
    }

    static BookingRoom from(UUID id, String number, BigDecimal dailyRate) {
        return new BookingRoom(id, number, dailyRate);
    }
}

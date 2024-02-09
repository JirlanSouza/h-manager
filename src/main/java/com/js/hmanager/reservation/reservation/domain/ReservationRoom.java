package com.js.hmanager.reservation.reservation.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationRoom {
    private final UUID id;
    private String number;
    private BigDecimal dailyRate;

    public ReservationRoom(String number, BigDecimal dailyRate) {
        this.id = UUID.randomUUID();
        this.number = number;
        this.dailyRate = dailyRate;
    }

    static ReservationRoom from(UUID id, String number, BigDecimal dailyRate) {
        return new ReservationRoom(id, number, dailyRate);
    }
}

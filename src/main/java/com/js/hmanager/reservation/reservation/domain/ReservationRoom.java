package com.js.hmanager.reservation.reservation.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationRoom {
    private final UUID id;
    private UUID roomId;
    private String number;
    private ReservationPeriod period;
    private BigDecimal dailyRate;

    public ReservationRoom(UUID roomId, String number, OffsetDateTime checkIn, OffsetDateTime checkOut, BigDecimal dailyRate) {
        this.id = UUID.randomUUID();
        this.roomId = roomId;
        this.period = new ReservationPeriod(checkIn, checkOut);
        this.number = number;
        this.dailyRate = dailyRate;
    }

    static ReservationRoom from(UUID id, UUID roomId, String number, OffsetDateTime checkIn, OffsetDateTime checkOut, BigDecimal dailyRate) {
        return new ReservationRoom(id, roomId, number, new ReservationPeriod(checkIn, checkOut), dailyRate);
    }

    BigDecimal calculateTotalPrice() {
        int days = period.getDays();

        return dailyRate.multiply(BigDecimal.valueOf(days));
    }
}

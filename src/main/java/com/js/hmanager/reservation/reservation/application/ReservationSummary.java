package com.js.hmanager.reservation.reservation.application;

import com.js.hmanager.reservation.reservation.domain.ReservationStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ReservationSummary(
        UUID id,
        OffsetDateTime checkInDate,
        OffsetDateTime checkOutDate,

        int roomsAmount,
        ReservationStatus status,
        BigDecimal totalPrice
) {
}

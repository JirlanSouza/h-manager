package com.js.hmanager.reservation.reservation.application;

import com.js.hmanager.reservation.reservation.domain.BookingStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record BookingSummary(
        UUID id,
        OffsetDateTime checkInDate,
        OffsetDateTime checkOutDate,

        int roomsAmount,
        BookingStatus status,
        BigDecimal totalPrice
) {
}

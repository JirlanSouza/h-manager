package com.js.hmanager.booking.booking.application;

import com.js.hmanager.booking.booking.domain.BookingStatus;

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

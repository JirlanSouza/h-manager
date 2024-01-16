package com.js.hmanager.booking.domain.application.booking;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record CreateBookingDto(
        UUID customerId,
        OffsetDateTime checkinDate,
        OffsetDateTime checkoutDate,
        List<UUID> roomIds
) {
}

package com.js.hmanager.booking.domain.application.comands;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record CreateBookingCommand(
        UUID customerId,
        OffsetDateTime checkinDate,
        OffsetDateTime checkoutDate,
        List<UUID> roomIds
) {
}

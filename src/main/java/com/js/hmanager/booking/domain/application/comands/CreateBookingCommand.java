package com.js.hmanager.booking.domain.application.comands;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CreateBookingCommand(
        UUID customerId,
        LocalDateTime checkinDate,
        LocalDateTime checkoutDate,
        List<UUID> roomIds
) {
}

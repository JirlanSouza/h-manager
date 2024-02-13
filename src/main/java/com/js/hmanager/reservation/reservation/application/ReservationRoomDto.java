package com.js.hmanager.reservation.reservation.application;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReservationRoomDto(UUID id, OffsetDateTime checkIn, OffsetDateTime checkOut) {
}

package com.js.hmanager.reservation.reservation.application;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record CreateReservationDto(
        UUID customerId,
        List<ReservationRoomDto> rooms
) {
}
package com.js.hmanager.reservation.reservation.application;

import java.math.BigDecimal;
import java.util.UUID;

public record ReservationRoomData(UUID id, String number, BigDecimal dailyRate) {
}

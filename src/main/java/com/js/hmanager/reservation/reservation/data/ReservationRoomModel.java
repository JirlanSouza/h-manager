package com.js.hmanager.reservation.reservation.data;

import com.js.hmanager.reservation.reservation.domain.ReservationRoom;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations_rooms")
public class ReservationRoomModel {
    @Id
    private UUID id;
    private String number;
    private OffsetDateTime checkInDate;
    private OffsetDateTime checkOutDate;
    private BigDecimal dailyRate;

    @Column(name = "reservation_id")
    private UUID reservationId;

    public ReservationRoomModel(UUID reservationId, ReservationRoom room) {
        this(
                room.getId(),
                room.getNumber(),
                room.getPeriod().checkIn(),
                room.getPeriod().checkOut(),
                room.getDailyRate(),
                reservationId
        );
    }
}

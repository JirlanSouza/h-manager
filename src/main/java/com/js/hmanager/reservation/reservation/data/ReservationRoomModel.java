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
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings_rooms")
public class ReservationRoomModel {
    @Id
    private UUID id;
    private String number;
    private BigDecimal dailyRate;

    @Column(name = "booking_id")
    private UUID bookingId;

    public ReservationRoomModel(UUID bookingId, ReservationRoom room) {
        this(room.getId(), room.getNumber(), room.getDailyRate(), bookingId);
    }
}

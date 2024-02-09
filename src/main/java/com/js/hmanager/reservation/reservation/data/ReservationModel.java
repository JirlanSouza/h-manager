package com.js.hmanager.reservation.reservation.data;

import com.js.hmanager.reservation.reservation.domain.Reservation;
import com.js.hmanager.reservation.reservation.domain.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class ReservationModel {
    @Id
    private UUID id;
    private OffsetDateTime checkInDate;
    private OffsetDateTime checkOutDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "booking_id")
    private List<ReservationRoomModel> roms;
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public ReservationModel(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getRoms().stream().map(
                        room -> new ReservationRoomModel(reservation.getId(), room)
                ).toList(),
                reservation.getTotalPrice(),
                reservation.getStatus()
        );
    }
}

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
@Table(name = "reservations")
public class ReservationModel {
    @Id
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "reservation_id")
    private List<ReservationRoomModel> roms;
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public ReservationModel(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getRoms().stream().map(
                        room -> new ReservationRoomModel(reservation.getId(), room)
                ).toList(),
                reservation.getTotalPrice(),
                reservation.getStatus()
        );
    }
}

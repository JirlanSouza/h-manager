package com.js.hmanager.reservation.reservation.domain;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation {
    private final UUID id;
    private List<ReservationRoom> roms;
    private ReservationStatus status;
    private BigDecimal totalPrice;

    public Reservation(List<ReservationRoom> rooms) {

        if (rooms.isEmpty()) {
            throw new InvalidArgumentDomainException("The rooms list must have one or more rooms");
        }

        this.id = UUID.randomUUID();
        this.roms = rooms;
        this.status = ReservationStatus.CREATED;
        calculateTotalPrice();
    }

    public static Reservation restore(
            UUID id,
            List<ReservationRoom> roms,
            ReservationStatus status,
            BigDecimal totalPrice
    ) {
        return new Reservation(id, roms, status, totalPrice);
    }

    public void addRoom(ReservationRoom room) {
        this.roms.add(room);
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        totalPrice = BigDecimal.ZERO;

        for (ReservationRoom r : roms) {
            totalPrice = totalPrice.add(r.calculateTotalPrice());
        }
    }


}

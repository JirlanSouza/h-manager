package com.js.hmanager.booking.domain.model.entity;

import com.js.hmanager.booking.domain.model.enums.BookingStatus;
import com.js.hmanager.sharad.domainExceptions.InvalidArgumentDomainException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Booking {
    private final UUID id;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private List<Room> roms;
    private BigDecimal totalPrice;
    private BookingStatus status;

    public Booking(LocalDateTime checkIn, LocalDateTime checkOut, List<Room> rooms) {
        if (rooms.isEmpty()) throw new InvalidArgumentDomainException("The rooms list must have one or more rooms");

        this.id = UUID.randomUUID();
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
        this.roms = rooms;
        this.totalPrice = BigDecimal.ZERO;
    }

    public static Booking restore(
            UUID id,
            LocalDateTime checkIn,
            LocalDateTime checkOut,
            List<Room> roms,
            BigDecimal totalPrice,
            BookingStatus status
    ) {
        return new Booking(id, checkIn, checkOut, roms, totalPrice, status);
    }

    public void addRoom(Room room) {
        this.roms.add(room);
    }

    public void changeCheckinDate(LocalDateTime newCheckInDate) {
        Period periodToCheckinDate = Period.between(
                LocalDate.now(),
                checkInDate.toLocalDate()
        );

        if (periodToCheckinDate.isNegative()) {
            throw new InvalidArgumentDomainException("The check-in date has passed");
        }

        if (periodToCheckinDate.getDays() > 7) {
            throw new InvalidArgumentDomainException("The period of change check-in date has expired");
        }

        this.checkInDate = newCheckInDate;
    }

    public BigDecimal getTotalPrice() {
        calculateTotalPrice();
        return totalPrice;
    }

    public void changeCheckOutDate(LocalDateTime newCheckOutDate) {
        Period periodToCheckOutDate = Period.between(
                LocalDate.now(),
                checkOutDate.toLocalDate()
        );

        if (periodToCheckOutDate.getDays() == 0) {
            throw new InvalidArgumentDomainException("the checkout date is already the same");
        }

        this.checkOutDate = newCheckOutDate;
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        Period periodOfStay = Period.between(checkInDate.toLocalDate(), checkOutDate.toLocalDate());
        totalPrice = BigDecimal.ZERO;

        roms.forEach(r -> {
            totalPrice = totalPrice.add(
                    r.getDailyTax().multiply(BigDecimal.valueOf(periodOfStay.getDays()))
            );
        });
    }
}

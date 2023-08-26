package com.js.hmanager.booking.domain.model.booking;

import com.js.hmanager.sharad.domainExceptions.InvalidArgumentDomainException;
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
public class Booking {
    private final UUID id;
    private OffsetDateTime checkInDate;
    private OffsetDateTime checkOutDate;
    private List<Room> roms;
    private BigDecimal totalPrice;
    private BookingStatus status;

    public Booking(OffsetDateTime checkIn, OffsetDateTime checkOut, List<Room> rooms) {
        if (rooms.isEmpty()) throw new InvalidArgumentDomainException("The rooms list must have one or more rooms");

        this.id = UUID.randomUUID();
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
        this.roms = rooms;
        this.totalPrice = BigDecimal.ZERO;
    }

    public static Booking restore(
            UUID id,
            OffsetDateTime checkIn,
            OffsetDateTime checkOut,
            List<Room> roms,
            BigDecimal totalPrice,
            BookingStatus status
    ) {
        return new Booking(id, checkIn, checkOut, roms, totalPrice, status);
    }

    public void addRoom(Room room) {
        this.roms.add(room);
    }

    public void changeCheckinDate(OffsetDateTime newCheckInDate) {
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

    public void changeCheckOutDate(OffsetDateTime newCheckOutDate) {
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
        long daysOfStay = calculateDaysOfStay();
        totalPrice = BigDecimal.ZERO;

        for (Room r : roms) {
            totalPrice = totalPrice.add(r.getDailyRate().multiply(BigDecimal.valueOf(daysOfStay)));
        }
    }

    private long calculateDaysOfStay() {
        long days = checkInDate.toLocalDate().until(checkOutDate.toLocalDate(), ChronoUnit.DAYS);

        if (checkInDate.getHour() <= 11) days++;
        if (checkOutDate.getHour() >= 12) days++;

        return days;
    }
}

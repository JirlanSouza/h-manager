package com.js.hmanager.reservation.reservation.domain;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;

import java.time.OffsetDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public record ReservationPeriod(OffsetDateTime checkIn, OffsetDateTime checkOut) {

    public ReservationPeriod {
        validatePeriod(checkIn, checkOut);
    }

    public int getGays() {
        int days = (int) checkIn.toLocalDate().until(checkOut.toLocalDate(), ChronoUnit.DAYS);

        if (checkIn.getHour() <= 11) days++;
        if (checkOut.getHour() >= 12) days++;

        return days;
    }

    private void validatePeriod(OffsetDateTime checkIn, OffsetDateTime checkOut) {
        Period reservationPeriod = Period.between(
                checkIn.toLocalDate(),
                checkOut.toLocalDate()
        );

        if (reservationPeriod.getDays() <= 0) {
            throw new InvalidArgumentDomainException("The hosting period has to be more than 1 day");
        }
    }
}

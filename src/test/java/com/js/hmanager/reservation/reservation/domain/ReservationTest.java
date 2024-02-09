package com.js.hmanager.reservation.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationTest {

    private OffsetDateTime makeDateTime(int year, Month month, int dayOfMonth, int hour, int minute, ZoneOffset zoneOffset) {
        return OffsetDateTime.of(year, month.getValue(), dayOfMonth, hour, minute, 0, 0, zoneOffset);
    }

    @Test
    @DisplayName("Should restore with all data")
    void restore() {
        UUID id = UUID.randomUUID();
        OffsetDateTime checkIn = makeDateTime(2023, Month.AUGUST, 1, 14, 0, ZoneOffset.UTC);
        OffsetDateTime checkOut = makeDateTime(2023, Month.AUGUST, 5, 8, 30, ZoneOffset.UTC);
        ArrayList<ReservationRoom> rooms = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal(0);

        Reservation reservation = Reservation.restore(id, checkIn, checkOut, rooms, ReservationStatus.CREATED, totalPrice);

        assertEquals(reservation.getId(), id);
        assertEquals(reservation.getCheckInDate(), checkIn);
        assertEquals(reservation.getCheckOutDate(), checkOut);
        assertEquals(reservation.getRoms().size(), rooms.size());
        assertEquals(reservation.getTotalPrice(), totalPrice);
        assertEquals(reservation.getStatus(), ReservationStatus.CREATED);
    }

    @Test
    @DisplayName("Should not create room with checkin after checkout date")
    void roomWithCheckinAfterCheckoutDate() {
        OffsetDateTime checkIn = makeDateTime(2023, Month.AUGUST, 6, 14, 0, ZoneOffset.UTC);
        OffsetDateTime checkOut = makeDateTime(2023, Month.AUGUST, 5, 8, 30, ZoneOffset.UTC);
        List<ReservationRoom> rooms = List.of(new ReservationRoom("1002", new BigDecimal("325.99")));


        assertThatThrownBy(() -> new Reservation(checkIn, checkOut, rooms))
                .isInstanceOf(InvalidArgumentDomainException.class)
                .hasMessage("The hosting period has to be more than 1 day");
    }

    @Test
    @DisplayName("Should update totalPrice when add new room")
    void addRoom() {
        ArrayList<ReservationRoom> rooms = new ArrayList<>();
        rooms.add(new ReservationRoom("1001", new BigDecimal("225.99")));
        Reservation reservation = new Reservation(
                makeDateTime(2023, Month.AUGUST, 1, 14, 0, ZoneOffset.UTC),
                makeDateTime(2023, Month.AUGUST, 3, 8, 30, ZoneOffset.UTC),
                rooms
        );

        ReservationRoom room = new ReservationRoom("1002", new BigDecimal("325.99"));

        assertEquals(reservation.getTotalPrice(), new BigDecimal("451.98"));

        reservation.addRoom(room);

        assertEquals(reservation.getTotalPrice(), new BigDecimal("1103.96"));
    }

    @Test
    @DisplayName("Should calculate the correct total price when the reservation has a checkin and checkout in normal datetime")
    void calculateTotalPrice() {
        ArrayList<ReservationRoom> rooms = new ArrayList<>();
        rooms.add(new ReservationRoom("1001", new BigDecimal("100.00")));
        Reservation reservation = new Reservation(
                makeDateTime(2023, Month.AUGUST, 1, 15, 0, ZoneOffset.UTC),
                makeDateTime(2023, Month.AUGUST, 3, 11, 30, ZoneOffset.UTC),
                rooms
        );

        assertEquals(new BigDecimal("200.00"), reservation.getTotalPrice());
    }

    @Test
    @DisplayName("Should calculate the correct total price when the reservation has a checking datetime before the start time")
    void calculateTotalPriceToBeforeCheckinStartTime() {
        ArrayList<ReservationRoom> rooms = new ArrayList<>();
        rooms.add(new ReservationRoom("1001", new BigDecimal("100.00")));
        Reservation reservation = new Reservation(
                makeDateTime(2023, Month.AUGUST, 1, 9, 40, ZoneOffset.UTC),
                makeDateTime(2023, Month.AUGUST, 3, 11, 30, ZoneOffset.UTC),
                rooms
        );

        assertEquals(new BigDecimal("300.00"), reservation.getTotalPrice());
    }

    @Test
    @DisplayName("Should calculate the correct total price when the reservation has a checkout datetime after the end time")
    void calculateTotalPriceToAfterCheckoutEndTime() {
        ArrayList<ReservationRoom> rooms = new ArrayList<>();
        rooms.add(new ReservationRoom("1001", new BigDecimal("100.00")));
        Reservation reservation = new Reservation(
                makeDateTime(2023, Month.AUGUST, 1, 15, 0, ZoneOffset.UTC),
                makeDateTime(2023, Month.AUGUST, 3, 14, 30, ZoneOffset.UTC),
                rooms
        );

        assertEquals(new BigDecimal("300.00"), reservation.getTotalPrice());
    }

    @Test
    @DisplayName("Should calculate the correct total price when the reservation has a checkout datetime after the end time")
    void calculateTotalPriceToBeforeCheckinStartTimeANdAfterCheckoutEndTime() {
        ArrayList<ReservationRoom> rooms = new ArrayList<>();
        rooms.add(new ReservationRoom("1001", new BigDecimal("100.00")));
        Reservation reservation = new Reservation(
                makeDateTime(2023, Month.AUGUST, 1, 10, 0, ZoneOffset.UTC),
                makeDateTime(2023, Month.AUGUST, 3, 15, 0, ZoneOffset.UTC),
                rooms
        );

        assertEquals(new BigDecimal("400.00"), reservation.getTotalPrice());
    }
}
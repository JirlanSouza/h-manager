package com.js.hmanager.booking.booking.domain;

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

class BookingTest {

    private OffsetDateTime makeDateTime(int year, Month month, int dayOfMonth, int hour, int minute, ZoneOffset zoneOffset) {
        return OffsetDateTime.of(year, month.getValue(), dayOfMonth, hour, minute, 0, 0, zoneOffset);
    }

    @Test
    @DisplayName("Should restore with all data")
    void restore() {
        UUID id = UUID.randomUUID();
        OffsetDateTime checkIn = makeDateTime(2023, Month.AUGUST, 1, 14, 0, ZoneOffset.UTC);
        OffsetDateTime checkOut = makeDateTime(2023, Month.AUGUST, 5, 8, 30, ZoneOffset.UTC);
        ArrayList<BookingRoom> rooms = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal(0);

        Booking booking = Booking.restore(id, checkIn, checkOut, rooms, BookingStatus.CREATED, totalPrice);

        assertEquals(booking.getId(), id);
        assertEquals(booking.getCheckInDate(), checkIn);
        assertEquals(booking.getCheckOutDate(), checkOut);
        assertEquals(booking.getRoms().size(), rooms.size());
        assertEquals(booking.getTotalPrice(), totalPrice);
        assertEquals(booking.getStatus(), BookingStatus.CREATED);
    }

    @Test
    @DisplayName("Should not create room with checkin after checkout date")
    void roomWithCheckinAfterCheckoutDate() {
        OffsetDateTime checkIn = makeDateTime(2023, Month.AUGUST, 6, 14, 0, ZoneOffset.UTC);
        OffsetDateTime checkOut = makeDateTime(2023, Month.AUGUST, 5, 8, 30, ZoneOffset.UTC);
        List<BookingRoom> rooms = List.of(new BookingRoom("1002", new BigDecimal("325.99")));


        assertThatThrownBy(() -> new Booking(checkIn, checkOut, rooms))
                .isInstanceOf(InvalidArgumentDomainException.class)
                .hasMessage("The hosting period has to be more than 1 day");
    }

    @Test
    @DisplayName("Should update totalPrice when add new room")
    void addRoom() {
        ArrayList<BookingRoom> rooms = new ArrayList<>();
        rooms.add(new BookingRoom("1001", new BigDecimal("225.99")));
        Booking booking = new Booking(
                makeDateTime(2023, Month.AUGUST, 1, 14, 0, ZoneOffset.UTC),
                makeDateTime(2023, Month.AUGUST, 3, 8, 30, ZoneOffset.UTC),
                rooms
        );

        BookingRoom room = new BookingRoom("1002", new BigDecimal("325.99"));

        assertEquals(booking.getTotalPrice(), new BigDecimal("451.98"));

        booking.addRoom(room);

        assertEquals(booking.getTotalPrice(), new BigDecimal("1103.96"));
    }

    @Test
    @DisplayName("Should calculate the correct total price when the booking has a checkin and checkout in normal datetime")
    void calculateTotalPrice() {
        ArrayList<BookingRoom> rooms = new ArrayList<>();
        rooms.add(new BookingRoom("1001", new BigDecimal("100.00")));
        Booking booking = new Booking(
                makeDateTime(2023, Month.AUGUST, 1, 15, 0, ZoneOffset.UTC),
                makeDateTime(2023, Month.AUGUST, 3, 11, 30, ZoneOffset.UTC),
                rooms
        );

        assertEquals(new BigDecimal("200.00"), booking.getTotalPrice());
    }

    @Test
    @DisplayName("Should calculate the correct total price when the booking has a checking datetime before the start time")
    void calculateTotalPriceToBeforeCheckinStartTime() {
        ArrayList<BookingRoom> rooms = new ArrayList<>();
        rooms.add(new BookingRoom("1001", new BigDecimal("100.00")));
        Booking booking = new Booking(
                makeDateTime(2023, Month.AUGUST, 1, 9, 40, ZoneOffset.UTC),
                makeDateTime(2023, Month.AUGUST, 3, 11, 30, ZoneOffset.UTC),
                rooms
        );

        assertEquals(new BigDecimal("300.00"), booking.getTotalPrice());
    }

    @Test
    @DisplayName("Should calculate the correct total price when the booking has a checkout datetime after the end time")
    void calculateTotalPriceToAfterCheckoutEndTime() {
        ArrayList<BookingRoom> rooms = new ArrayList<>();
        rooms.add(new BookingRoom("1001", new BigDecimal("100.00")));
        Booking booking = new Booking(
                makeDateTime(2023, Month.AUGUST, 1, 15, 0, ZoneOffset.UTC),
                makeDateTime(2023, Month.AUGUST, 3, 14, 30, ZoneOffset.UTC),
                rooms
        );

        assertEquals(new BigDecimal("300.00"), booking.getTotalPrice());
    }

    @Test
    @DisplayName("Should calculate the correct total price when the booking has a checkout datetime after the end time")
    void calculateTotalPriceToBeforeCheckinStartTimeANdAfterCheckoutEndTime() {
        ArrayList<BookingRoom> rooms = new ArrayList<>();
        rooms.add(new BookingRoom("1001", new BigDecimal("100.00")));
        Booking booking = new Booking(
                makeDateTime(2023, Month.AUGUST, 1, 10, 0, ZoneOffset.UTC),
                makeDateTime(2023, Month.AUGUST, 3, 15, 0, ZoneOffset.UTC),
                rooms
        );

        assertEquals(new BigDecimal("400.00"), booking.getTotalPrice());
    }
}
package com.js.hmanager.booking.domain.entity;

import com.js.hmanager.booking.domain.model.booking.Booking;
import com.js.hmanager.booking.domain.model.booking.Room;
import com.js.hmanager.booking.domain.model.booking.BookingStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingTest {

    @Test
    @DisplayName("Should restore with all data")
    void restore() {
        UUID id = UUID.randomUUID();
        LocalDateTime checkIn = LocalDateTime.of(2023, Month.AUGUST, 1, 14, 0);
        LocalDateTime checkOut = LocalDateTime.of(2023, Month.AUGUST, 5, 8, 30);
        ArrayList<Room> rooms = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal(0);

        Booking booking = Booking.restore(id, checkIn, checkOut, rooms, totalPrice, BookingStatus.CREATED);

        assertEquals(booking.getId(), id);
        assertEquals(booking.getCheckInDate(), checkIn);
        assertEquals(booking.getCheckOutDate(), checkOut);
        assertEquals(booking.getRoms().size(), rooms.size());
        assertEquals(booking.getTotalPrice(), totalPrice);
        assertEquals(booking.getStatus(), BookingStatus.CREATED);
    }

    @Test
    @DisplayName("Should update totalPrice when add new room")
    void addRoom() {
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room("1001", new BigDecimal("225.99")));
        Booking booking = new Booking(
                LocalDateTime.of(2023, Month.AUGUST, 1, 14, 0),
                LocalDateTime.of(2023, Month.AUGUST, 3, 8, 30),
                rooms
        );

        Room room = new Room("1002", new BigDecimal("325.99"));

        assertEquals(booking.getTotalPrice(), new BigDecimal("451.98"));

        booking.addRoom(room);

        assertEquals(booking.getTotalPrice(), new BigDecimal("1103.96"));
    }
}
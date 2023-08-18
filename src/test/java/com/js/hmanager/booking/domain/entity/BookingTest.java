package com.js.hmanager.booking.domain.entity;

import com.js.hmanager.booking.domain.enums.BookingStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
}
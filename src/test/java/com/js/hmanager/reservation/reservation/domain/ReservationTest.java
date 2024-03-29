package com.js.hmanager.reservation.reservation.domain;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    private OffsetDateTime makeDateTime(int year, Month month, int dayOfMonth, int hour, int minute) {
        return OffsetDateTime.of(year, month.getValue(), dayOfMonth, hour, minute, 0, 0, ZoneOffset.UTC);
    }

    @Test
    @DisplayName("Should restore with all data")
    void restore() {
        UUID id = UUID.randomUUID();
        ArrayList<ReservationRoom> rooms = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal(0);

        Reservation reservation = Reservation.restore(id, rooms, ReservationStatus.CREATED, totalPrice);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(reservation.getId()).as("Id is equals").isEqualTo(id);
            softly.assertThat(reservation.getRoms().size()).as("Rooms list size is equals").isEqualTo(rooms.size());
            softly.assertThat(reservation.getTotalPrice()).as("Total price is equals").isEqualTo(totalPrice);
            softly.assertThat(reservation.getStatus()).as("Status is equals").isEqualTo(ReservationStatus.CREATED);
        });
    }

    @Test
    @DisplayName("Should not create room with checkin after checkout date")
    void roomWithCheckinAfterCheckoutDate() {
        OffsetDateTime checkIn = makeDateTime(2023, Month.AUGUST, 6, 14, 0);
        OffsetDateTime checkOut = makeDateTime(2023, Month.AUGUST, 5, 8, 30);
        List<ReservationRoom> rooms = List.of(new ReservationRoom(
                UUID.randomUUID(), "1002", checkIn, checkOut, new BigDecimal("325.99"))
        );


        assertThatThrownBy(() -> new Reservation(rooms))
                .isInstanceOf(InvalidArgumentDomainException.class)
                .hasMessage("The hosting period has to be more than 1 day");
    }

    @Test
    @DisplayName("Should update totalPrice when add new room")
    void addRoom() {
        OffsetDateTime checkIn = makeDateTime(2023, Month.AUGUST, 1, 14, 0);
        OffsetDateTime checkOut = makeDateTime(2023, Month.AUGUST, 3, 8, 30);
        ArrayList<ReservationRoom> rooms = new ArrayList<>();
        rooms.add(new ReservationRoom(UUID.randomUUID(), "1001", checkIn, checkOut, new BigDecimal("225.99")));
        Reservation reservation = new Reservation(rooms);

        ReservationRoom room = new ReservationRoom(UUID.randomUUID(), "1002", checkIn, checkOut, new BigDecimal("325.99"));

        assertThat(reservation.getTotalPrice()).as("Total price before add new room").isEqualTo(new BigDecimal("451.98"));
        reservation.addRoom(room);
        assertThat(reservation.getTotalPrice()).as("Total price after add new room").isEqualTo(new BigDecimal("1103.96"));
    }

    @Test
    @DisplayName("Should calculate the correct total price when the reservation has a checkin and checkout in normal datetime")
    void calculateTotalPrice() {
        OffsetDateTime checkIn = makeDateTime(2023, Month.AUGUST, 1, 15, 0);
        OffsetDateTime checkOut = makeDateTime(2023, Month.AUGUST, 3, 11, 30);
        ArrayList<ReservationRoom> rooms = new ArrayList<>();
        rooms.add(new ReservationRoom(UUID.randomUUID(), "1001", checkIn, checkOut, new BigDecimal("100.00")));
        Reservation reservation = new Reservation(rooms);

        assertThat(reservation.getTotalPrice()).isEqualTo(new BigDecimal("200.00"));
    }

    @Test
    @DisplayName("Should calculate the correct total price when the reservation has a checking datetime before the start time")
    void calculateTotalPriceToBeforeCheckinStartTime() {
        OffsetDateTime checkIn = makeDateTime(2023, Month.AUGUST, 1, 9, 40);
        OffsetDateTime checkOut = makeDateTime(2023, Month.AUGUST, 3, 11, 30);
        ArrayList<ReservationRoom> rooms = new ArrayList<>();
        rooms.add(new ReservationRoom(UUID.randomUUID(), "1001", checkIn, checkOut, new BigDecimal("100.00")));
        Reservation reservation = new Reservation(rooms);

        assertThat(reservation.getTotalPrice()).isEqualTo(new BigDecimal("300.00"));
    }

    @Test
    @DisplayName("Should calculate the correct total price when the reservation has a checkout datetime after the end time")
    void calculateTotalPriceToAfterCheckoutEndTime() {
        OffsetDateTime checkIn = makeDateTime(2023, Month.AUGUST, 1, 15, 0);
        OffsetDateTime checkOut = makeDateTime(2023, Month.AUGUST, 3, 14, 30);
        ArrayList<ReservationRoom> rooms = new ArrayList<>();
        rooms.add(new ReservationRoom(UUID.randomUUID(), "1001", checkIn, checkOut, new BigDecimal("100.00")));
        Reservation reservation = new Reservation(rooms);

        assertThat(reservation.getTotalPrice()).isEqualTo(new BigDecimal("300.00"));
    }

    @Test
    @DisplayName("Should calculate the correct total price when the reservation has a checkout datetime after the end time")
    void calculateTotalPriceToBeforeCheckinStartTimeANdAfterCheckoutEndTime() {
        OffsetDateTime checkIn = makeDateTime(2023, Month.AUGUST, 1, 10, 0);
        OffsetDateTime checkOut = makeDateTime(2023, Month.AUGUST, 3, 15, 0);
        ArrayList<ReservationRoom> rooms = new ArrayList<>();
        rooms.add(new ReservationRoom(UUID.randomUUID(), "1001", checkIn, checkOut, new BigDecimal("100.00")));
        Reservation reservation = new Reservation(rooms);

        assertThat(reservation.getTotalPrice()).isEqualTo(new BigDecimal("400.00"));
    }
}
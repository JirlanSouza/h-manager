package com.js.hmanager.booking.booking.data;

import com.js.hmanager.booking.booking.domain.BookingRoom;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings_rooms")
public class BookingRoomModel {
    @Id
    private UUID id;
    private String number;
    private BigDecimal dailyRate;

    @Column(name = "booking_id")
    private UUID bookingId;

    public BookingRoomModel(UUID bookingId, BookingRoom room) {
        this(room.getId(), room.getNumber(), room.getDailyRate(), bookingId);
    }
}

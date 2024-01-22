package com.js.hmanager.booking.booking.data;

import com.js.hmanager.booking.booking.domain.Booking;
import com.js.hmanager.booking.booking.domain.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class BookingModel {
    @Id
    private UUID id;
    private OffsetDateTime checkInDate;
    private OffsetDateTime checkOutDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "booking_id")
    private List<BookingRoomModel> roms;
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public BookingModel(Booking booking) {
        this(
                booking.getId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getRoms().stream().map(
                        room -> new BookingRoomModel(booking.getId(), room)
                ).toList(),
                booking.getTotalPrice(),
                booking.getStatus()
        );
    }
}

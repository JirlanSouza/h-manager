package com.js.hmanager.booking.domain.application.comandHandlers;

import com.js.hmanager.booking.domain.application.comands.CreateBookingCommand;
import com.js.hmanager.booking.domain.model.entity.Booking;
import com.js.hmanager.booking.domain.model.repository.BookingRepository;

import java.util.UUID;

public class CreateBookingHandler {
    private final BookingRepository bookingRepository;

    public CreateBookingHandler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public UUID handle(CreateBookingCommand command) {
        Booking booking = new Booking(command.checkinDate(), command.checkoutDate());

        bookingRepository.save(booking);

        return booking.getId();
    }
}

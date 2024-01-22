package com.js.hmanager.booking.booking.rest;

import com.js.hmanager.booking.booking.application.CreateBooking;
import com.js.hmanager.booking.booking.application.CreateBookingDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final CreateBooking createBooking;

    public BookingController(CreateBooking createBooking) {
        this.createBooking = createBooking;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createBooking(@RequestBody CreateBookingDto bookingDto) {
        return this.createBooking.execute(bookingDto);
    }
}

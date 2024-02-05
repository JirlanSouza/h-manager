package com.js.hmanager.booking.booking.rest;

import com.js.hmanager.booking.booking.application.BookingQueryService;
import com.js.hmanager.booking.booking.application.BookingSummary;
import com.js.hmanager.booking.booking.application.CreateBooking;
import com.js.hmanager.booking.booking.application.CreateBookingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final CreateBooking createBooking;
    private final BookingQueryService bookingQueryService;

    public BookingController(CreateBooking createBooking, BookingQueryService bookingQueryService) {
        this.createBooking = createBooking;
        this.bookingQueryService = bookingQueryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createBooking(@RequestBody CreateBookingDto bookingDto) {
        return this.createBooking.execute(bookingDto);
    }

    @GetMapping
    public Page<BookingSummary> getBookingsSummary(Pageable pageable) {
        return this.bookingQueryService.getBookingsSummary(pageable);
    }
}

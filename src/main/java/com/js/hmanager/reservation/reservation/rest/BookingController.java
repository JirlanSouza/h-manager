package com.js.hmanager.reservation.reservation.rest;

import com.js.hmanager.reservation.reservation.application.BookingQueryService;
import com.js.hmanager.reservation.reservation.application.BookingSummary;
import com.js.hmanager.reservation.reservation.application.CreateBooking;
import com.js.hmanager.reservation.reservation.application.CreateBookingDto;
import com.js.hmanager.common.data.DataPage;
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
    public DataPage<BookingSummary> getBookingsSummary(Pageable pageable) {
        return this.bookingQueryService.getBookingsSummary(pageable);
    }
}

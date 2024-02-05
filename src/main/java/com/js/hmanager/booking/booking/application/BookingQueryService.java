package com.js.hmanager.booking.booking.application;

import com.js.hmanager.booking.booking.data.BookingQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookingQueryService {
    private final BookingQueryRepository bookingQueryRepository;

    public BookingQueryService(BookingQueryRepository bookingQueryRepository) {
        this.bookingQueryRepository = bookingQueryRepository;
    }

    public Page<BookingSummary> getBookingsSummary(Pageable pageable) {
        try {
        return this.bookingQueryRepository.listSummary(pageable);

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
}

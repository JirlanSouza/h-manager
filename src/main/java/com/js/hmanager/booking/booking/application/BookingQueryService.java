package com.js.hmanager.booking.booking.application;

import com.js.hmanager.booking.booking.data.BookingQueryRepository;
import com.js.hmanager.common.data.DataPage;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookingQueryService {
    private final BookingQueryRepository bookingQueryRepository;

    public BookingQueryService(BookingQueryRepository bookingQueryRepository) {
        this.bookingQueryRepository = bookingQueryRepository;
    }

    public DataPage<BookingSummary> getBookingsSummary(Pageable pageable) {
        return this.bookingQueryRepository.listSummary(pageable);
    }
}

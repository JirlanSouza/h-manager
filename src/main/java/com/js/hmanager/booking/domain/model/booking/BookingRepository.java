package com.js.hmanager.booking.domain.model.booking;

import com.js.hmanager.booking.domain.model.booking.Booking;

public interface BookingRepository {
    void save(Booking booking);
}

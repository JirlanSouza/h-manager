package com.js.hmanager.booking.domain.model.repository;

import com.js.hmanager.booking.domain.model.entity.Booking;

public interface BookingRepository {
    void save(Booking booking);
}

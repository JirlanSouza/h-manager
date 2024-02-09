package com.js.hmanager.reservation.reservation.data;

import com.js.hmanager.reservation.reservation.domain.Booking;
import com.js.hmanager.reservation.reservation.domain.BookingRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingJpaRepository extends JpaRepository<BookingModel, UUID>, BookingRepository {

    @Override
    default void save(Booking booking) {
        save(new BookingModel(booking));
    }
}

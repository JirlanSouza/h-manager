package com.js.hmanager.reservation.reservation.data;

import com.js.hmanager.reservation.reservation.domain.Reservation;
import com.js.hmanager.reservation.reservation.domain.ReservationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationModel, UUID>, ReservationRepository {

    @Override
    default void save(Reservation reservation) {
        save(new ReservationModel(reservation));
    }
}

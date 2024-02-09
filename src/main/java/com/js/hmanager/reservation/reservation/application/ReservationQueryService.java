package com.js.hmanager.reservation.reservation.application;

import com.js.hmanager.reservation.reservation.data.ReservationQueryRepository;
import com.js.hmanager.common.data.DataPage;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReservationQueryService {
    private final ReservationQueryRepository reservationQueryRepository;

    public ReservationQueryService(ReservationQueryRepository reservationQueryRepository) {
        this.reservationQueryRepository = reservationQueryRepository;
    }

    public DataPage<ReservationSummary> getBookingsSummary(Pageable pageable) {
        return this.reservationQueryRepository.listSummary(pageable);
    }
}

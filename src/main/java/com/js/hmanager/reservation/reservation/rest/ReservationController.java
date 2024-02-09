package com.js.hmanager.reservation.reservation.rest;

import com.js.hmanager.reservation.reservation.application.ReservationQueryService;
import com.js.hmanager.reservation.reservation.application.ReservationSummary;
import com.js.hmanager.reservation.reservation.application.CreateReservationService;
import com.js.hmanager.reservation.reservation.application.CreateReservationDto;
import com.js.hmanager.common.data.DataPage;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final CreateReservationService createReservationService;
    private final ReservationQueryService reservationQueryService;

    public ReservationController(CreateReservationService createReservationService, ReservationQueryService reservationQueryService) {
        this.createReservationService = createReservationService;
        this.reservationQueryService = reservationQueryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createReservation(@RequestBody CreateReservationDto reservationDto) {
        return this.createReservationService.execute(reservationDto);
    }

    @GetMapping
    public DataPage<ReservationSummary> getReservationsSummary(Pageable pageable) {
        return this.reservationQueryService.getReservationsSummary(pageable);
    }
}

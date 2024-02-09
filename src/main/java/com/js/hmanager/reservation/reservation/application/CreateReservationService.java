package com.js.hmanager.reservation.reservation.application;

import com.js.hmanager.reservation.reservation.application.adapters.InventoryService;
import com.js.hmanager.reservation.reservation.domain.Reservation;
import com.js.hmanager.reservation.reservation.domain.ReservationRepository;
import com.js.hmanager.reservation.reservation.domain.ReservationRoom;
import com.js.hmanager.reservation.customer.domain.CustomerRepository;
import com.js.hmanager.common.domainExceptions.NotFoundEntityDomainException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreateReservationService {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final InventoryService inventoryService;

    public CreateReservationService(
            ReservationRepository reservationRepository,
            CustomerRepository customerRepository,
            InventoryService inventoryService
    ) {
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.inventoryService = inventoryService;
    }

    public UUID execute(CreateReservationDto reservationData) {
        boolean existsCustomer = customerRepository.exists(reservationData.customerId());

        if (!existsCustomer) {
            throw new NotFoundEntityDomainException(
                    "The customer with id: '%s' does not exists".formatted(reservationData.customerId())
            );
        }

        List<ReservationRoom> rooms = inventoryService.findRooms(reservationData.roomIds());
        this.validateAllRoomsExists(rooms, reservationData.roomIds());

        Reservation reservation = new Reservation(reservationData.checkinDate(), reservationData.checkoutDate(), rooms);

        reservationRepository.save(reservation);

        return reservation.getId();
    }

    private void validateAllRoomsExists(List<ReservationRoom> rooms, List<UUID> roomsIds) {
        if (rooms.size() == roomsIds.size()) {
            return;
        }

        StringBuilder builder = new StringBuilder();

        roomsIds.stream()
                .filter(
                        roomId -> rooms.stream()
                                .noneMatch(room -> room.getId().equals(roomId))
                ).forEach(roomId -> builder.append(roomId).append(", "));

        builder.delete(builder.length() - 2, builder.length() - 1);

        throw new NotFoundEntityDomainException(
                "The rooms withs ids: %s does not exists".formatted(builder.toString().trim())
        );
    }
}

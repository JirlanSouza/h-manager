package com.js.hmanager.reservation.reservation.application;

import com.js.hmanager.reservation.reservation.application.adapters.InventoryService;
import com.js.hmanager.reservation.reservation.domain.Reservation;
import com.js.hmanager.reservation.reservation.domain.ReservationRepository;
import com.js.hmanager.reservation.customer.domain.CustomerRepository;
import com.js.hmanager.common.domainExceptions.NotFoundEntityDomainException;
import com.js.hmanager.reservation.reservation.domain.ReservationRoom;
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

        List<ReservationRoomData> roomsData = inventoryService.findRooms(
                reservationData.rooms().stream().map(ReservationRoomDto::id).toList()
        );

        validateAllRoomsExists(roomsData, reservationData.rooms());

        List<ReservationRoom> rooms = roomsData.stream().map(roomData -> {
            ReservationRoomDto roomDto = reservationData.rooms().stream()
                    .filter(r -> r.id().equals(roomData.id()))
                    .findFirst().get();

            return new ReservationRoom(
                    roomData.id(),
                    roomData.number(),
                    roomDto.checkIn(),
                    roomDto.checkOut(),
                    roomData.dailyRate());
        }).toList();

        Reservation reservation = new Reservation(rooms);
        reservationRepository.save(reservation);

        return reservation.getId();
    }

    private void validateAllRoomsExists(List<ReservationRoomData> roomsData, List<ReservationRoomDto> roomsDto) {
        if (roomsData.size() == roomsDto.size()) {
            return;
        }

        StringBuilder builder = new StringBuilder();

        roomsDto.stream()
                .filter(
                        roomDto -> roomsData.stream()
                                .noneMatch(room -> room.id().equals(roomDto.id()))
                ).forEach(roomId -> builder.append(roomId).append(", "));

        builder.delete(builder.length() - 2, builder.length() - 1);

        throw new NotFoundEntityDomainException(
                "The rooms withs ids: %s does not exists".formatted(builder.toString().trim())
        );
    }
}

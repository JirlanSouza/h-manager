package com.js.hmanager.reservation.reservation.application;

import com.js.hmanager.reservation.reservation.application.adapters.InventoryService;
import com.js.hmanager.reservation.reservation.domain.Reservation;
import com.js.hmanager.reservation.reservation.domain.ReservationRepository;
import com.js.hmanager.reservation.reservation.domain.ReservationRoom;
import com.js.hmanager.reservation.customer.domain.CustomerRepository;
import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;
import com.js.hmanager.common.domainExceptions.NotFoundEntityDomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateReservationTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    InventoryService inventoryService;

    @InjectMocks
    private CreateReservationService createReservationServiceHandler;

    @Test
    @DisplayName("Should create a new reservation")
    void createNewReservation() {
        CreateReservationDto command = new CreateReservationDto(
                UUID.randomUUID(),
                OffsetDateTime.now(),
                OffsetDateTime.now().plusDays(2),
                List.of(UUID.randomUUID())
        );

        when(customerRepository.exists(isA(UUID.class))).thenReturn(true);
        when(inventoryService.findRooms(anyList()))
                .thenReturn(List.of(new ReservationRoom("1001", new BigDecimal("220.99"))));

        UUID reservationId = createReservationServiceHandler.execute(command);

        assertNotNull(reservationId);
        verify(reservationRepository, only()).save(isA(Reservation.class));
    }

    @Test
    @DisplayName("Should throw NotFoundEntityDomainException when creating a reservation with a no existent customer")
    void createReservationWithNonexistentCustomer() {
        CreateReservationDto command = new CreateReservationDto(
                UUID.randomUUID(),
                OffsetDateTime.now(),
                OffsetDateTime.now().plusDays(2),
                List.of(UUID.randomUUID())
        );

        assertThrows(NotFoundEntityDomainException.class, () -> createReservationServiceHandler.execute(command));
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomain exception when creating a reservation with an empty room list")
    void createReservationWithEmptyRoomList() {
        CreateReservationDto command = new CreateReservationDto(
                UUID.randomUUID(),
                OffsetDateTime.now(),
                OffsetDateTime.now().plusDays(2),
                List.of()
        );

        when(customerRepository.exists(isA(UUID.class))).thenReturn(true);

        assertThrows(InvalidArgumentDomainException.class, () -> createReservationServiceHandler.execute(command));
    }
}
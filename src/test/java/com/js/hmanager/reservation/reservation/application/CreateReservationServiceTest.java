package com.js.hmanager.reservation.reservation.application;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;
import com.js.hmanager.common.domainExceptions.NotFoundEntityDomainException;
import com.js.hmanager.reservation.customer.domain.CustomerRepository;
import com.js.hmanager.reservation.reservation.application.adapters.InventoryService;
import com.js.hmanager.reservation.reservation.domain.Reservation;
import com.js.hmanager.reservation.reservation.domain.ReservationRepository;
import com.js.hmanager.reservation.reservation.domain.ReservationRoom;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    InventoryService inventoryService;

    @InjectMocks
    private CreateReservationService createReservationService;

    @Test
    @DisplayName("Should create a new reservation")
    void createNewReservation() {
        CreateReservationDto reservationDto = new CreateReservationDto(
                UUID.randomUUID(),
                OffsetDateTime.now(),
                OffsetDateTime.now().plusDays(2),
                List.of(UUID.randomUUID())
        );

        when(customerRepository.exists(isA(UUID.class))).thenReturn(true);
        when(inventoryService.findRooms(anyList()))
                .thenReturn(List.of(new ReservationRoom("1001", new BigDecimal("220.99"))));

        UUID reservationId = createReservationService.execute(reservationDto);

        assertThat(reservationId).isNotNull();
        verify(reservationRepository, only()).save(isA(Reservation.class));
    }

    @Test
    @DisplayName("Should throw NotFoundEntityDomainException when creating a reservation with a no existent customer")
    void createReservationWithNonexistentCustomer() {
        CreateReservationDto reservationDto = new CreateReservationDto(
                UUID.randomUUID(),
                OffsetDateTime.now(),
                OffsetDateTime.now().plusDays(2),
                List.of(UUID.randomUUID())
        );

        assertThatThrownBy(() -> createReservationService.execute(reservationDto))
                .isInstanceOf(NotFoundEntityDomainException.class)
                .hasMessage("The customer with id: '%s' does not exists".formatted(reservationDto.customerId()));
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

        assertThatThrownBy(() -> createReservationService.execute(command))
                .isInstanceOf(InvalidArgumentDomainException.class)
                .hasMessage("The rooms list must have one or more rooms");
    }
}
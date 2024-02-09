package com.js.hmanager.reservation.reservation.application;

import com.js.hmanager.reservation.reservation.application.adapters.InventoryService;
import com.js.hmanager.reservation.reservation.domain.Booking;
import com.js.hmanager.reservation.reservation.domain.BookingRepository;
import com.js.hmanager.reservation.reservation.domain.BookingRoom;
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
class CreateBookingTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    InventoryService inventoryService;

    @InjectMocks
    private CreateBooking createBookingHandler;

    @Test
    @DisplayName("Should create a new booking")
    void createNewBooking() {
        CreateBookingDto command = new CreateBookingDto(
                UUID.randomUUID(),
                OffsetDateTime.now(),
                OffsetDateTime.now().plusDays(2),
                List.of(UUID.randomUUID())
        );

        when(customerRepository.exists(isA(UUID.class))).thenReturn(true);
        when(inventoryService.findRooms(anyList()))
                .thenReturn(List.of(new BookingRoom("1001", new BigDecimal("220.99"))));

        UUID bookingId = createBookingHandler.execute(command);

        assertNotNull(bookingId);
        verify(bookingRepository, only()).save(isA(Booking.class));
    }

    @Test
    @DisplayName("Should throw NotFoundEntityDomainException when creating a booking with a no existent customer")
    void createBookingWithNonexistentCustomer() {
        CreateBookingDto command = new CreateBookingDto(
                UUID.randomUUID(),
                OffsetDateTime.now(),
                OffsetDateTime.now().plusDays(2),
                List.of(UUID.randomUUID())
        );

        assertThrows(NotFoundEntityDomainException.class, () -> createBookingHandler.execute(command));
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomain exception when creating a booking with an empty room list")
    void createBookingWithEmptyRoomList() {
        CreateBookingDto command = new CreateBookingDto(
                UUID.randomUUID(),
                OffsetDateTime.now(),
                OffsetDateTime.now().plusDays(2),
                List.of()
        );

        when(customerRepository.exists(isA(UUID.class))).thenReturn(true);

        assertThrows(InvalidArgumentDomainException.class, () -> createBookingHandler.execute(command));
    }
}
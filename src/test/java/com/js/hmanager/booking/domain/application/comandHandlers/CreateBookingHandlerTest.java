package com.js.hmanager.booking.domain.application.comandHandlers;

import com.js.hmanager.booking.domain.application.adapters.InventoryService;
import com.js.hmanager.booking.domain.application.comands.CreateBookingCommand;
import com.js.hmanager.booking.domain.model.entity.Booking;
import com.js.hmanager.booking.domain.model.entity.Room;
import com.js.hmanager.booking.domain.model.repository.BookingRepository;
import com.js.hmanager.booking.domain.model.repository.CustomerRepository;
import com.js.hmanager.sharad.domainExceptions.InvalidArgumentDomainException;
import com.js.hmanager.sharad.domainExceptions.NotFoundEntityDomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateBookingHandlerTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    InventoryService inventoryService;

    @InjectMocks
    private CreateBookingHandler createBookingHandler;

    @Test
    @DisplayName("Should create a new booking")
    void createNewBooking() {
        CreateBookingCommand command = new CreateBookingCommand(
                UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2),
                List.of(UUID.randomUUID())
        );

        when(customerRepository.exists(Mockito.isA(UUID.class))).thenReturn(true);
        when(inventoryService.findRooms(Mockito.anyList()))
                .thenReturn(List.of(new Room("1001", new BigDecimal("220.99"))));

        UUID bookingId = createBookingHandler.handle(command);

        assertNotNull(bookingId);
        verify(bookingRepository, Mockito.only()).save(Mockito.isA(Booking.class));
    }

    @Test
    @DisplayName("Should throw NotFoundEntityDomainException when creating a booking with a no existent customer")
    void createBookingWithNonexistentCustomer() {
        CreateBookingCommand command = new CreateBookingCommand(
                UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2),
                List.of(UUID.randomUUID())
        );

        assertThrows(NotFoundEntityDomainException.class, () -> createBookingHandler.handle(command));
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomain exception when creating a booking with an empty room list")
    void createBookingWithEmptyRoomList() {
        CreateBookingCommand command = new CreateBookingCommand(
                UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2),
                List.of()
        );

        when(customerRepository.exists(Mockito.isA(UUID.class))).thenReturn(true);

        assertThrows(InvalidArgumentDomainException.class, () -> createBookingHandler.handle(command));
    }
}
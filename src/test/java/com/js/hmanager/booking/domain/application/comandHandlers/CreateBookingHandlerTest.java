package com.js.hmanager.booking.domain.application.comandHandlers;

import com.js.hmanager.booking.domain.application.comands.CreateBookingCommand;
import com.js.hmanager.booking.domain.model.entity.Booking;
import com.js.hmanager.booking.domain.model.repository.BookingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateBookingHandlerTest {

    @Mock
    private BookingRepository bookingRepository;

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

        UUID bookingId = createBookingHandler.handle(command);

        assertNotNull(bookingId);
        verify(bookingRepository, Mockito.only()).save(Mockito.isA(Booking.class));
    }
}
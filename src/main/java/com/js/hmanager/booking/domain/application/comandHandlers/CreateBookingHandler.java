package com.js.hmanager.booking.domain.application.comandHandlers;

import com.js.hmanager.booking.domain.application.adapters.InventoryService;
import com.js.hmanager.booking.domain.application.comands.CreateBookingCommand;
import com.js.hmanager.booking.domain.model.booking.Booking;
import com.js.hmanager.booking.domain.model.booking.Room;
import com.js.hmanager.booking.domain.model.booking.BookingRepository;
import com.js.hmanager.booking.domain.model.customer.CustomerRepository;
import com.js.hmanager.sharad.domainExceptions.NotFoundEntityDomainException;

import java.util.List;
import java.util.UUID;

public class CreateBookingHandler {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final InventoryService inventoryService;

    public CreateBookingHandler(
            BookingRepository bookingRepository,
            CustomerRepository customerRepository,
            InventoryService inventoryService
    ) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.inventoryService = inventoryService;
    }

    public UUID handle(CreateBookingCommand command) {
        boolean existsCustomer = customerRepository.exists(command.customerId());

        if (!existsCustomer) {
            throw new NotFoundEntityDomainException(
                    "The customer with id: '%s' does not exists".formatted(command.customerId())
            );
        }

        List<Room> rooms = inventoryService.findRooms(command.roomIds());
        Booking booking = new Booking(command.checkinDate(), command.checkoutDate(), rooms);

        bookingRepository.save(booking);

        return booking.getId();
    }
}

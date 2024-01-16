package com.js.hmanager.booking.domain.application.booking;

import com.js.hmanager.booking.domain.application.adapters.InventoryService;
import com.js.hmanager.booking.domain.model.booking.Booking;
import com.js.hmanager.booking.domain.model.booking.BookingRepository;
import com.js.hmanager.booking.domain.model.booking.Room;
import com.js.hmanager.booking.domain.model.customer.CustomerRepository;
import com.js.hmanager.common.domainExceptions.NotFoundEntityDomainException;

import java.util.List;
import java.util.UUID;

public class CreateBooking {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final InventoryService inventoryService;

    public CreateBooking(
            BookingRepository bookingRepository,
            CustomerRepository customerRepository,
            InventoryService inventoryService
    ) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.inventoryService = inventoryService;
    }

    public UUID execute(CreateBookingDto bookingData) {
        boolean existsCustomer = customerRepository.exists(bookingData.customerId());

        if (!existsCustomer) {
            throw new NotFoundEntityDomainException(
                    "The customer with id: '%s' does not exists".formatted(bookingData.customerId())
            );
        }

        List<Room> rooms = inventoryService.findRooms(bookingData.roomIds());
        Booking booking = new Booking(bookingData.checkinDate(), bookingData.checkoutDate(), rooms);

        bookingRepository.save(booking);

        return booking.getId();
    }
}

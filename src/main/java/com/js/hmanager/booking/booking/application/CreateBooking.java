package com.js.hmanager.booking.booking.application;

import com.js.hmanager.booking.booking.application.adapters.InventoryService;
import com.js.hmanager.booking.booking.domain.Booking;
import com.js.hmanager.booking.booking.domain.BookingRepository;
import com.js.hmanager.booking.booking.domain.BookingRoom;
import com.js.hmanager.booking.customer.domain.CustomerRepository;
import com.js.hmanager.common.domainExceptions.NotFoundEntityDomainException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
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

        List<BookingRoom> rooms = inventoryService.findRooms(bookingData.roomIds());
        Booking booking = new Booking(bookingData.checkinDate(), bookingData.checkoutDate(), rooms);

        bookingRepository.save(booking);

        return booking.getId();
    }
}

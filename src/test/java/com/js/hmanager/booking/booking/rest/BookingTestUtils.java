package com.js.hmanager.booking.booking.rest;

import com.js.hmanager.booking.booking.data.BookingJpaRepository;
import com.js.hmanager.booking.booking.data.BookingModel;
import com.js.hmanager.booking.booking.data.BookingRoomModel;
import com.js.hmanager.booking.booking.domain.BookingStatus;
import com.js.hmanager.booking.customer.data.CustomerJpaRepository;
import com.js.hmanager.booking.customer.data.CustomerModel;
import com.js.hmanager.inventory.data.RoomJpaRepository;
import com.js.hmanager.inventory.data.RoomModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


@Component
public class BookingTestUtils {

    @Autowired
    private CustomerJpaRepository customerRepository;

    @Autowired
    private RoomJpaRepository roomRepository;

    @Autowired
    private BookingJpaRepository bookingRepository;

    public UUID createCustomerIntoDatabase() {
        CustomerModel customerModel = CustomerModel.builder()
                .id(UUID.randomUUID())
                .name("Joe Jho")
                .cpf("111.444.777-35")
                .email("joejho@gmail.com")
                .telephone("65985890067")
                .addressStreet("Test street")
                .addressNumber("999")
                .addressNeighborhood("Test neighborhood")
                .addressZipCode("12356-458")
                .addressCity("Test city")
                .addressState("Test state")
                .addressCountry("Test country")
                .build();

        customerRepository.save(customerModel);

        return customerModel.getId();
    }

    public List<RoomModel> createRoomsIntoDatabase(int quantity) {
        AtomicInteger roomNumber = new AtomicInteger(1);
        List<RoomModel> roomModels = Stream.generate(() -> roomNumber.getAndIncrement() + 1000)
                .limit(quantity)
                .map(rn -> {
                    return RoomModel.builder()
                            .id(UUID.randomUUID())
                            .number(rn.toString())
                            .doubleBeds(1)
                            .singleBeds(0)
                            .dailyRate(BigDecimal.valueOf(220.00))
                            .available(true)
                            .build();

                }).toList();

        roomRepository.saveAll(roomModels);

        return roomModels;
    }

    public List<BookingModel> createBookingsIntoDatabase() {
        List<RoomModel> roomModels = createRoomsIntoDatabase(6);
        List<BookingModel> bookingModels = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            UUID bookingId = UUID.randomUUID();
            List<BookingRoomModel> bookingRoomModels = roomModels.stream()
                    .skip((i % 2 == 0 ? 1 : 2))
                    .limit(i % 2 == 0 ? 1 : 2)
                    .map(rm -> new BookingRoomModel(
                            UUID.randomUUID(),
                            rm.getNumber(),
                            rm.getDailyRate(),
                            bookingId
                    )).toList();

            BigDecimal bookingTotalPrice = new BigDecimal(0);
            for (BookingRoomModel bookingRoomModel : bookingRoomModels) {
                bookingTotalPrice = bookingTotalPrice.add(bookingRoomModel.getDailyRate());
            }

            bookingModels.add(new BookingModel(
                    bookingId,
                    OffsetDateTime.now().plusDays(5),
                    OffsetDateTime.now().plusDays(8),
                    bookingRoomModels,
                    bookingTotalPrice,
                    BookingStatus.CREATED
            ));
        }

        bookingRepository.saveAll(bookingModels);
        return bookingModels;
    }
}

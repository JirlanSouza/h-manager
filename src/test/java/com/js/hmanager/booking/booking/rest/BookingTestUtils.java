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
import java.util.List;
import java.util.UUID;
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

    public List<UUID> createRoomsIntoDatabase() {
        RoomModel roomModel = RoomModel.builder()
                .id(UUID.randomUUID())
                .number("1010")
                .doubleBeds(1)
                .singleBeds(0)
                .dailyRate(BigDecimal.valueOf(220.00))
                .available(true)
                .build();

        roomRepository.save(roomModel);

        return List.of(roomModel.getId());
    }

    public List<BookingModel> createBookingsIntoDatabase() {

        List<BookingModel> bookingModels = Stream.generate(() -> {

            List<UUID> roomsIds = createRoomsIntoDatabase();
            UUID bookingId = UUID.randomUUID();
            List<BookingRoomModel> bookingRoomModels = roomsIds.stream()
                    .map(roomId -> new BookingRoomModel(
                            UUID.randomUUID(),
                            "1010",
                            BigDecimal.valueOf(200.00),
                            bookingId
                    )).toList();

            BigDecimal bookingTotalPrice = new BigDecimal(0);
            for (BookingRoomModel bookingRoomModel : bookingRoomModels) {
                bookingTotalPrice = bookingTotalPrice.add(bookingRoomModel.getDailyRate());
            }

            return new BookingModel(
                    bookingId,
                    OffsetDateTime.now().plusDays(5),
                    OffsetDateTime.now().plusDays(8),
                    bookingRoomModels,
                    bookingTotalPrice,
                    BookingStatus.CREATED
            );
        }).limit(4).toList();

        bookingRepository.saveAll(bookingModels);
        return bookingModels;
    }
}

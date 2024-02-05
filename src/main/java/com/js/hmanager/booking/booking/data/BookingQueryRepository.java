package com.js.hmanager.booking.booking.data;

import com.js.hmanager.booking.booking.application.BookingSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingQueryRepository extends PagingAndSortingRepository<BookingModel, UUID> {

    @Query("""
            select new com.js.hmanager.booking.booking.application.BookingSummary(
                b.id,
                b.checkInDate,
                b.checkOutDate,
                SIZE(b.roms),
                b.status,
                b.totalPrice
            )
            from BookingModel as b
        """)
    Page<BookingSummary> listSummary(Pageable pageable);
}

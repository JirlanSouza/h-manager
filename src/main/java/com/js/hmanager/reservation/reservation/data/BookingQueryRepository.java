package com.js.hmanager.reservation.reservation.data;

import com.js.hmanager.reservation.reservation.application.BookingSummary;
import com.js.hmanager.common.data.DataPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingQueryRepository extends PagingAndSortingRepository<BookingModel, UUID> {

    @Query("""
                select new com.js.hmanager.reservation.reservation.application.BookingSummary(
                    b.id,
                    b.checkInDate,
                    b.checkOutDate,
                    SIZE(b.roms),
                    b.status,
                    b.totalPrice
                )
                from BookingModel as b
            """)
    Page<BookingSummary> findAllSummary(Pageable pageable);

    default DataPage<BookingSummary> listSummary(Pageable pageable) {
        return DataPage.from(findAllSummary(pageable));
    }
}

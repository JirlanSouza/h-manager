package com.js.hmanager.reservation.reservation.data;

import com.js.hmanager.reservation.reservation.application.ReservationSummary;
import com.js.hmanager.common.data.DataPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationQueryRepository extends PagingAndSortingRepository<ReservationModel, UUID> {

    @Query("""
                select new com.js.hmanager.reservation.reservation.application.ReservationSummary(
                    r.id,
                    r.checkInDate,
                    r.checkOutDate,
                    SIZE(r.roms),
                    r.status,
                    r.totalPrice
                )
                from ReservationModel as r
            """)
    Page<ReservationSummary> findAllSummary(Pageable pageable);

    default DataPage<ReservationSummary> listSummary(Pageable pageable) {
        return DataPage.from(findAllSummary(pageable));
    }
}

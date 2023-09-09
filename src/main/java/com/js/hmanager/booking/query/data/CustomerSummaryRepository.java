package com.js.hmanager.booking.query.data;

import com.js.hmanager.booking.query.model.CustomerSummary;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerSummaryRepository extends PagingAndSortingRepository<CustomerSummary, UUID> {
}

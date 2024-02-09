package com.js.hmanager.reservation.customer.data;


import com.js.hmanager.reservation.customer.application.CustomerSummary;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerSummaryRepository extends PagingAndSortingRepository<CustomerSummary, UUID> {
}

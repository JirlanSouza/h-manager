package com.js.hmanager.booking.query.handlers;

import com.js.hmanager.booking.query.data.CustomerSummaryRepository;
import com.js.hmanager.booking.query.model.CustomerSummary;
import com.js.hmanager.booking.query.model.CustomerSummaryResponse;
import com.js.hmanager.booking.query.model.CustomersSummaryQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CustomerSummaryHandler {
    private final CustomerSummaryRepository repository;

    public CustomerSummaryHandler(CustomerSummaryRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    public CustomerSummaryResponse handle(CustomersSummaryQuery query) {

        Page<CustomerSummary> customerSummaries = repository.findAll(query.pageable());

        return new CustomerSummaryResponse(
                customerSummaries.getNumber(),
                customerSummaries.getSize(),
                customerSummaries.getTotalElements(),
                customerSummaries.isFirst(),
                customerSummaries.isLast(),
                customerSummaries.isEmpty(),
                customerSummaries.getContent()
        );
    }

}

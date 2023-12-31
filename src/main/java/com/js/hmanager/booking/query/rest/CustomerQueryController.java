package com.js.hmanager.booking.query.rest;

import com.js.hmanager.booking.query.model.CustomerSummaryResponse;
import com.js.hmanager.booking.query.model.CustomersSummaryQuery;
import org.axonframework.queryhandling.QueryGateway;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/customers")
public class CustomerQueryController {
    private final QueryGateway queryGateway;

    public CustomerQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public CustomerSummaryResponse getCustomerSummary(
            @ParameterObject Pageable pageable
    ) throws ExecutionException, InterruptedException {
        return queryGateway.query(new CustomersSummaryQuery(pageable), CustomerSummaryResponse.class).get();
    }
}

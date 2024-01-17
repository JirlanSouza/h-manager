package com.js.hmanager.booking.customer.application;

import com.js.hmanager.booking.customer.data.CustomerSummaryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerSummaryService {
    private final CustomerSummaryRepository repository;

    public CustomerSummaryService(CustomerSummaryRepository repository) {
        this.repository = repository;
    }

    public CustomerSummaryPresenter all(Pageable pageable) {

        Page<CustomerSummary> customerSummaries = repository.findAll(pageable);

        return new CustomerSummaryPresenter(
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

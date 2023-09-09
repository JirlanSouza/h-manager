package com.js.hmanager.booking.query.model;

import org.springframework.data.domain.Page;

import java.util.List;

public record CustomerSummaryResponse(
        int number,
        int size,
        long totalItems,
        boolean isFirst,
        boolean isLast,
        boolean isEmpty,
        List<CustomerSummary> content
) {
}

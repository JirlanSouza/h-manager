package com.js.hmanager.reservation.customer.application;

import java.util.List;

public record CustomerSummaryPresenter(
        int number,
        int size,
        long totalItems,
        boolean isFirst,
        boolean isLast,
        boolean isEmpty,
        List<CustomerSummary> content
) {
}

package com.js.hmanager.common.data;

import org.springframework.data.domain.Page;

import java.util.List;

public record DataPage<T>(List<T> data, int size, int number, int totalItems, int totalPages) {
    public static <T> DataPage<T> from(Page<T> page) {
        return new DataPage<>(
                page.getContent(),
                page.getSize(),
                page.getNumber(),
                page.getNumberOfElements(),
                page.getTotalPages()
        );
    }
}

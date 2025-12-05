package com.fpmislata.daw.tienda.domain.model;

import java.util.List;

public record Page<T>(
        List<T> data,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages) {

    public Page(List<T> data, int pageNumber, int pageSize, long totalElements) {
        this(
                validateDataSize(data, pageSize),
                validatePageNumber(pageNumber),
                validatePageSize(pageSize),
                totalElements,
                (int) Math.ceil((double) totalElements / pageSize));
    }

    private static <T> List<T> validateDataSize(List<T> data, int pageSize) {
        if (data.size() > pageSize) {
            throw new RuntimeException("Data size exceeds page size");
        }
        return data;
    }

    private static int validatePageNumber(int pageNumber) {
        if (pageNumber < 1) {
            throw new RuntimeException("Page number cannot be less than 1");
        }
        return pageNumber;
    }

    private static int validatePageSize(int pageSize) {
        if (pageSize <= 0) {
            throw new RuntimeException("Page size must be greater than 0");
        }
        return pageSize;
    }
}

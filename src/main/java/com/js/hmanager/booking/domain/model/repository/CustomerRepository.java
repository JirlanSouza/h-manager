package com.js.hmanager.booking.domain.model.repository;

import java.util.UUID;

public interface CustomerRepository {
    boolean exists(UUID id);
}

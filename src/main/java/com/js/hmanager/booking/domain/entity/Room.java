package com.js.hmanager.booking.domain.entity;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Room {
    private UUID id;
    private String number;
    private BigDecimal dailyTax;
}

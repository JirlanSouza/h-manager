package com.js.hmanager.inventory.domain.model.room;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Room {
    private final UUID id;
    private String number;
    private RoomBeds beds;
    private BigDecimal dailyTax;
    private boolean available;

    public Room(String number, int doubleBeds, int singleBeds, BigDecimal dailyTax, boolean available) {
        this(UUID.randomUUID(), number, doubleBeds, singleBeds, dailyTax, available);
    }

    public Room(UUID id, String number, int doubleBeds, int singleBeds, BigDecimal dailyTax, boolean available) {
        this.id = id;
        this.number = number;
        this.beds = new RoomBeds(doubleBeds, singleBeds);
        this.dailyTax = dailyTax;
        this.available = available;

    }

    public static Room restore(
            UUID id,
            String number,
            int doubleBeds,
            int singleBeds,
            BigDecimal dailyTax,
            boolean available
    ) {
        return new Room(id, number, doubleBeds, singleBeds, dailyTax, available);
    }

    public int getCapacity() {
        return beds.calculateCapacity();
    }

    public void turnAvailable() {
        available = true;
    }

    public void turnUnavailable() {
        available = false;
    }

}

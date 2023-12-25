package com.js.hmanager.inventory.domain.model.room;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class Room {
    private final UUID id;
    private String number;
    private RoomBeds beds;
    private BigDecimal dailyRate;
    private boolean available;

    public Room(String number, int doubleBeds, int singleBeds, BigDecimal dailyRate, boolean available) {
        this(UUID.randomUUID(), number, doubleBeds, singleBeds, dailyRate, available);
    }

    public Room(UUID id, String number, int doubleBeds, int singleBeds, BigDecimal dailyRate, boolean available) {
        this.id = id;
        this.number = number;
        this.beds = new RoomBeds(doubleBeds, singleBeds);
        this.dailyRate = dailyRate;
        this.available = available;
        
        validateDailyRate();
    }

    public static Room restore(
            UUID id,
            String number,
            int doubleBeds,
            int singleBeds,
            BigDecimal dailyRate,
            boolean available
    ) {
        return new Room(id, number, doubleBeds, singleBeds, dailyRate, available);
    }
    
    private void validateDailyRate() {
        if (dailyRate.doubleValue() <= 0) {
            throw new InvalidArgumentDomainException("The daily rate must be greater than 0.0");
        }
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

package com.js.hmanager.inventory.domain;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomBedsTest {

    @Test
    @DisplayName("Should create a new RoomBeds")
    void createNewRoomBad() {
        RoomBeds roomBeds = new RoomBeds(1, 2);

        assertNotNull(roomBeds);
    }

    @Test
    @DisplayName("Should calculate beds capacity")
    void calculateCapacity() {
        RoomBeds roomBeds = new RoomBeds(1, 2);

        assertEquals(roomBeds.calculateCapacity(), 4);
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomainException when create RoomBeds with negative quantity")
    void negativeQuantity() {
        assertThrows(InvalidArgumentDomainException.class, () -> new RoomBeds(1, -1));
        assertThrows(InvalidArgumentDomainException.class, () -> new RoomBeds(-2, 1));
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomainException when create RoomBeds with all zero quantity")
    void allZeroQuantity() {
        assertThrows(InvalidArgumentDomainException.class, () -> new RoomBeds(0, 0));
    }

}
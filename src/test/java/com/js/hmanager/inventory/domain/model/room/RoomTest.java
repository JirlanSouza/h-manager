package com.js.hmanager.inventory.domain.model.room;

import com.js.hmanager.sharad.domainExceptions.InvalidArgumentDomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    @DisplayName("Should create a new Room with id")
    void createNewRoom() {
        Room room = new Room("1001", 1, 0, new BigDecimal("200.00"), true);

        assertInstanceOf(UUID.class, room.getId());
    }

    @Test
    @DisplayName("Should restore the Room with existent id")
    void restoreRoom() {
        UUID id = UUID.randomUUID();
        Room room = Room.restore(id, "1001", 1, 0, new BigDecimal("200.00"), true);

        assertEquals(room.getId(), id);
    }

    @Test
    @DisplayName("Should throw InvalidArgumentException when creating a new Room with less than zero daily rate")
    void createRoomWithLessZeroDailyRate() {
        assertThrows(InvalidArgumentDomainException.class, () -> new Room(
                        "1001", 1, 0, new BigDecimal("0.00"), true
                )
        );
    }
}
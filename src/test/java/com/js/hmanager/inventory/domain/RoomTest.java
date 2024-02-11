package com.js.hmanager.inventory.domain;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoomTest {

    @Test
    @DisplayName("Should create a new Room with id")
    void createNewRoom() {
        Room room = new Room("1001", 1, 0, new BigDecimal("200.00"), true);

        assertThat(room.getId()).isInstanceOf(UUID.class);
    }

    @Test
    @DisplayName("Should restore the Room with existent id")
    void restoreRoom() {
        UUID id = UUID.randomUUID();
        Room room = Room.restore(id, "1001", 1, 0, new BigDecimal("200.00"), true);

        assertThat(id).isEqualTo(room.getId());
    }

    @Test
    @DisplayName("Should throw InvalidArgumentException when creating a new Room with less than zero daily rate")
    void createRoomWithLessZeroDailyRate() {
        assertThatThrownBy(() -> new Room(
                "1001", 1, 0, new BigDecimal("200.0"), true
        ))
                .isNotInstanceOf(InvalidArgumentDomainException.class);
    }
}
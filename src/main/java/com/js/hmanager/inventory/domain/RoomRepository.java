package com.js.hmanager.inventory.domain;

public interface RoomRepository {
    boolean existsByNumber(String roomNumber);
    void save(Room room);
}

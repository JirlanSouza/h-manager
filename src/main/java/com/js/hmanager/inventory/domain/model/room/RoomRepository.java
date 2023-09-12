package com.js.hmanager.inventory.domain.model.room;

public interface RoomRepository {
    boolean existsByNumber(String roomNumber);
    void save(Room room);
}

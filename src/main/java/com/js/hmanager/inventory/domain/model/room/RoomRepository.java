package com.js.hmanager.inventory.domain.model.room;

public interface RoomRepository {
    boolean existByNumber(String roomNumber);
    void save(Room room);
}

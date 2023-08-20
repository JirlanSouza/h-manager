package com.js.hmanager.booking.domain.application.adapters;

import com.js.hmanager.booking.domain.model.booking.Room;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    List<Room> findRooms(List<UUID> roomIds);
}

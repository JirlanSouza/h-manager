package com.js.hmanager.booking.booking.application.adapters;

import com.js.hmanager.booking.booking.domain.Room;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    List<Room> findRooms(List<UUID> roomIds);
}

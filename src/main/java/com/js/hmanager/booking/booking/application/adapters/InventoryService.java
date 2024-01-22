package com.js.hmanager.booking.booking.application.adapters;

import com.js.hmanager.booking.booking.domain.BookingRoom;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    List<BookingRoom> findRooms(List<UUID> roomIds);
}

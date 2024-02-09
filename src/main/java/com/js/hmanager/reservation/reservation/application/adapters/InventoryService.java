package com.js.hmanager.reservation.reservation.application.adapters;

import com.js.hmanager.reservation.reservation.domain.BookingRoom;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    List<BookingRoom> findRooms(List<UUID> roomIds);
}

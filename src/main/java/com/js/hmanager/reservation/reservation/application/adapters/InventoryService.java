package com.js.hmanager.reservation.reservation.application.adapters;

import com.js.hmanager.reservation.reservation.application.ReservationRoomData;
import com.js.hmanager.reservation.reservation.domain.ReservationRoom;

import java.util.List;
import java.util.UUID;

public interface InventoryService {
    List<ReservationRoomData> findRooms(List<UUID> roomIds);
}

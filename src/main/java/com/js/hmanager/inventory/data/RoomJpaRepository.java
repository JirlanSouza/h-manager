package com.js.hmanager.inventory.data;

import com.js.hmanager.reservation.reservation.application.adapters.InventoryService;
import com.js.hmanager.reservation.reservation.domain.ReservationRoom;
import com.js.hmanager.inventory.domain.Room;
import com.js.hmanager.inventory.domain.RoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomJpaRepository extends JpaRepository<RoomModel, UUID>, RoomRepository, InventoryService {
    boolean existsByNumber(String roomNumber);

    @Override
    default void save(Room room) {
        save(RoomModel.builder()
                .id(room.getId())
                .number(room.getNumber())
                .doubleBeds(room.getBeds().doubleBed())
                .singleBeds(room.getBeds().singleBed())
                .dailyRate(room.getDailyRate())
                .available(room.isAvailable())
                .build()
        );
    }

    @Override
    @Query("""
        select new com.js.hmanager.reservation.reservation.domain.BookingRoom(
                r.id,
                r.number,
                r.dailyRate
            )
        from RoomModel as r where r.id in :roomIds
        """)
    List<ReservationRoom> findRooms(List<UUID> roomIds);
}

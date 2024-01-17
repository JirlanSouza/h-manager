package com.js.hmanager.inventory.data;

import com.js.hmanager.inventory.domain.Room;
import com.js.hmanager.inventory.domain.RoomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomJpaRepository extends JpaRepository<RoomModel, UUID>, RoomRepository {
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
}

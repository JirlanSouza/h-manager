package com.js.hmanager.inventory.application;

import com.js.hmanager.common.domainExceptions.ConflictEntityDomainException;
import com.js.hmanager.inventory.domain.Room;
import com.js.hmanager.inventory.domain.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateRoom {
    private final RoomRepository roomRepository;

    public CreateRoom(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public UUID execute(CreateRoomDto roomData) {
        boolean existed = roomRepository.existsByNumber(roomData.number());

        if (existed) {
            throw new ConflictEntityDomainException("The room with houseNumber: '%s' already exists".formatted(roomData.number()));
        }

        Room room = new Room(
                roomData.number(),
                roomData.doubleBeds(),
                roomData.singleBeds(),
                roomData.dailyRate(),
                roomData.available()
        );

        roomRepository.save(room);

        return room.getId();
    }
}

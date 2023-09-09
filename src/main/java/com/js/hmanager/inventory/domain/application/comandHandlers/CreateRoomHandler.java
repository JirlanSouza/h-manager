package com.js.hmanager.inventory.domain.application.comandHandlers;

import com.js.hmanager.inventory.domain.application.commands.CreateRoomCommand;
import com.js.hmanager.inventory.domain.model.room.Room;
import com.js.hmanager.inventory.domain.model.room.RoomRepository;
import com.js.hmanager.sharad.domainExceptions.ConflictEntityDomainException;

import java.util.UUID;

public class CreateRoomHandler {
    private RoomRepository roomRepository;

    public UUID handle(CreateRoomCommand command) {
        boolean existed = roomRepository.existByNumber(command.number());

        if (existed) {
            throw new ConflictEntityDomainException("The room with houseNumber: '%s' already exists".formatted(command.number()));
        }

        Room room = new Room(
                command.number(),
                command.doubleBeds(),
                command.singleBeds(),
                command.dailyTax(),
                command.available()
        );

        roomRepository.save(room);

        return room.getId();

    }
}

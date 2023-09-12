package com.js.hmanager.inventory.domain.application.comandHandlers;

import com.js.hmanager.inventory.domain.application.commands.CreateRoomCommand;
import com.js.hmanager.inventory.domain.model.room.Room;
import com.js.hmanager.inventory.domain.model.room.RoomRepository;
import com.js.hmanager.sharad.domainExceptions.ConflictEntityDomainException;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateRoomHandler {
    private final RoomRepository roomRepository;

    public CreateRoomHandler(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @CommandHandler
    public UUID handle(CreateRoomCommand command) {
        boolean existed = roomRepository.existsByNumber(command.number());

        if (existed) {
            throw new ConflictEntityDomainException("The room with houseNumber: '%s' already exists".formatted(command.number()));
        }

        Room room = new Room(
                command.number(),
                command.doubleBeds(),
                command.singleBeds(),
                command.dailyRate(),
                command.available()
        );

        roomRepository.save(room);

        return room.getId();
    }
}

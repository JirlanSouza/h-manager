package com.js.hmanager.inventory.infra.rest;

import com.js.hmanager.inventory.domain.application.commands.CreateRoomCommand;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final CommandGateway commandGateway;

    public RoomController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createRoom(@RequestBody @Valid CreateRoomCommand command) {
        return commandGateway.sendAndWait(command);
    }
}

package com.js.hmanager.inventory.rest;

import com.js.hmanager.inventory.application.CreateRoom;
import com.js.hmanager.inventory.application.CreateRoomDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final CreateRoom createRoom;

    public RoomController(CreateRoom createRoom) {
        this.createRoom = createRoom;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createRoom(@RequestBody @Valid CreateRoomDto command) {
        return createRoom.execute(command);
    }
}

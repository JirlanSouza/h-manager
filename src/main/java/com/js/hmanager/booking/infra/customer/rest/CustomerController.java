package com.js.hmanager.booking.infra.customer.rest;

import com.js.hmanager.booking.domain.application.customer.CreateCustomerDto;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CommandGateway commandGateway;

    public CustomerController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createCustomer(@RequestBody @Valid CreateCustomerDto command) {
        return commandGateway.<UUID>sendAndWait(command);
    }
}

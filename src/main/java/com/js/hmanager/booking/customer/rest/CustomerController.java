package com.js.hmanager.booking.customer.rest;

import com.js.hmanager.booking.customer.application.CreateCustomer;
import com.js.hmanager.booking.customer.application.CreateCustomerDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CreateCustomer createCustomer;

    public CustomerController(CreateCustomer createCustomer) {
        this.createCustomer = createCustomer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createCustomer(@RequestBody @Valid CreateCustomerDto command) {
        return createCustomer.execute(command);
    }
}

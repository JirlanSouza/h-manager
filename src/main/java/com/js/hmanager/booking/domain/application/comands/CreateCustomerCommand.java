package com.js.hmanager.booking.domain.application.comands;

public record CreateCustomerCommand(String name, String cpf, CreateCustomerAddressDto address) {
}
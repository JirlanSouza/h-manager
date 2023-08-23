package com.js.hmanager.booking.domain.model.customer;

import com.js.hmanager.sharad.domainExceptions.InvalidArgumentDomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CpfTest {

    @Test
    @DisplayName("Should create a new valid cpf")
    void createNewCpf() {
        var cpf = new Cpf("111.444.777-35");
        assertEquals(cpf.value(), "111.444.777-35");
    }

    @Test
    @DisplayName("Should create a valid CPF with verifying digits 00")
    void createNewCpfWith00VerifyingDigits() {
        var cpf = new Cpf("650.137.840-00");
        assertEquals(cpf.value(), "650.137.840-00");
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomainException when creating cpf with null value")
    void throwErrorWhenCreateCpfWithNullValue() {
        assertThrows(InvalidArgumentDomainException.class, () -> new Cpf(null));
    }
    @Test
    @DisplayName("Should throw InvalidArgumentDomainException when creating invalid cpf")
    void throwErrorWhenCreateInvalidCpf() {
        assertThrows(InvalidArgumentDomainException.class, () -> new Cpf("011.444.777-35"));
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomainException when creating cpf with no numbers characters")
    void throwErrorWhenCreateCpfWithNoNumbersCharacters() {
        assertThrows(InvalidArgumentDomainException.class, () -> new Cpf("cpf.cpf.cpf-in"));
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomainException when creating cpf with more than 14 characters")
    void throwErrorWhenCreateCpfWithMore11Characters() {
        assertThrows(InvalidArgumentDomainException.class, () -> new Cpf("111.444.777.999"));
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomainException when creating cpf with all equal characters")
    void throwErrorWhenCreateCpfWithEqualsCharacters() {
        assertThrows(InvalidArgumentDomainException.class, () -> new Cpf("111.111.111-11"));
    }
}
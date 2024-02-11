package com.js.hmanager.reservation.customer.domain;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CpfTest {

    @Test
    @DisplayName("Should create a new valid cpf")
    void createNewCpf() {
        var cpf = new Cpf("111.444.777-35");
        assertThat(cpf.value()).isEqualTo("111.444.777-35");
    }

    @Test
    @DisplayName("Should create a valid CPF with verifying digits 00")
    void createNewCpfWith00VerifyingDigits() {
        var cpf = new Cpf("650.137.840-00");
        assertThat(cpf.value()).isEqualTo("650.137.840-00");
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomainException when creating cpf with null value")
    void throwErrorWhenCreateCpfWithNullValue() {
        assertThatThrownBy(() -> new Cpf(null))
                .isInstanceOf(InvalidArgumentDomainException.class);
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomainException when creating invalid cpf")
    void throwErrorWhenCreateInvalidCpf() {
        assertThatThrownBy(() -> new Cpf("011.444.777-35"))
                .isInstanceOf(InvalidArgumentDomainException.class);
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomainException when creating cpf with no numbers characters")
    void throwErrorWhenCreateCpfWithNoNumbersCharacters() {
        assertThatThrownBy(() -> new Cpf("cpf.cpf.cpf-in"))
                .isInstanceOf(InvalidArgumentDomainException.class);
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomainException when creating cpf with more than 14 characters")
    void throwErrorWhenCreateCpfWithMore11Characters() {
        assertThatThrownBy(() -> new Cpf("111.444.777.999"))
                .isInstanceOf(InvalidArgumentDomainException.class);
    }

    @Test
    @DisplayName("Should throw InvalidArgumentDomainException when creating cpf with all equal characters")
    void throwErrorWhenCreateCpfWithEqualsCharacters() {
        assertThatThrownBy(() -> new Cpf("111.111.111-11"))
                .isInstanceOf(InvalidArgumentDomainException.class);
    }
}
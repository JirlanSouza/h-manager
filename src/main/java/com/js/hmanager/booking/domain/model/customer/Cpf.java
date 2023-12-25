package com.js.hmanager.booking.domain.model.customer;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;

import java.util.Arrays;

public record Cpf(String value) {
    private static final int FIRST_DIGIT_FACTOR = 10;
    private static final int SECOND_DIGIT_FACTOR = 11;

    public Cpf(String value) {
        this.value = format(validOrThrow(value));
    }

    private String validOrThrow(String rawCpf) {
        if (rawCpf == null) {
            throw new InvalidArgumentDomainException("Invalid cpf");
        }

        String cpf = this.clearInvalidCharacters(rawCpf);
        if (!isValid(cpf)) {
            throw new InvalidArgumentDomainException("Invalid cpf: '%s'".formatted(rawCpf));
        }

        return cpf;
    }

    private boolean isValid(String cpf) {

        if (!this.isValidLength(cpf) || this.isAllTheSameCharacters(cpf)) {
            return false;
        }
        int firstDigit = calculateDigit(cpf, FIRST_DIGIT_FACTOR);
        int secondDigit = calculateDigit(cpf, SECOND_DIGIT_FACTOR);

        return checkDigits(cpf, firstDigit, secondDigit);
    }

    private String clearInvalidCharacters(String cpf) {
        return cpf.replaceAll("\\D+", "");
    }

    private boolean isValidLength(String cpf) {
        return cpf.length() == 11;
    }

    private boolean isAllTheSameCharacters(String cpf) {
        return Arrays.stream(cpf.split("")).allMatch((character) -> character.equals(cpf.substring(0, 1)));
    }

    private int calculateDigit(String cpf, int factor) {
        int total = 0;

        for (String value : cpf.split("")) {
            if (factor <= 1) continue;
            total += Integer.parseInt(value) * factor--;
        }

        return getDigit(total);
    }

    private int getDigit(int value) {
        int rest = value % 11;
        return rest < 2 ? 0 : 11 - rest;
    }

    private boolean checkDigits(String cpf, int firstDigit, int secondDigit) {
        String verifyingDigits = getVerifyingDigits(cpf);
        String calculatedVerifyingDigits = firstDigit + "" + secondDigit;

        return verifyingDigits.equals(calculatedVerifyingDigits);
    }

    private String getVerifyingDigits(String cpf) {
        return cpf.substring(9);
    }

    private String format(String cpf) {
        return "%s.%s.%s-%s".formatted(
                cpf.substring(0, 3),
                cpf.substring(3, 6),
                cpf.substring(6, 9),
                cpf.substring(9, 11)
        );
    }
}

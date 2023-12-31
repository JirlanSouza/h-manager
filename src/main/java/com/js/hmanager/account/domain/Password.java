package com.js.hmanager.account.domain;

import com.js.hmanager.common.domainExceptions.InvalidArgumentDomainException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
public class Password {
    private final String value;
    private final static String REGEX_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{6,}$";

    public Password(String value) {
        validate(value);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.value = passwordEncoder.encode(value);
    }

    private Password(String value, boolean isRestore) {
        validate(value);
        this.value = value;
    }

    public static Password restore(String value) {
        return new Password(value, true);
    }

    private void validate(String password) {
        if (password == null) {
            throw new InvalidArgumentDomainException("The password cannot be null");
        }

        Pattern pattern = Pattern.compile(REGEX_PATTERN);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            throw new InvalidArgumentDomainException(
                    "The password to have 6 or more characters, one uppercase letter, one number and one especial character"
            );
        }
    }
}
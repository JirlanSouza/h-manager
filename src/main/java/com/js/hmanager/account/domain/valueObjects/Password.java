package com.js.hmanager.account.domain.valueObjects;

import com.js.hmanager.sharad.domainExceptions.InvalidArgumentDomainException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
public class Password {
    private final String value;
    private final String salt;
    private final static int NUM_ITERATIONS = 100;
    private final static int LENGTH = 64;
    private final static String REGEX_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{6,}$";

    public Password(String value) {
        validate(value);

        try {
            this.salt = generateSalt();
            this.value = hash(value, salt);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Password(String value, String salt) {
        validate(value);

        this.value = value;
        this.salt = salt;
    }

    public static Password restore(String value, String salt) {
        return new Password(value, salt);
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

    private String generateSalt() {
        return getArrayByteString(SecureRandom.getSeed(64));
    }

    private String hash(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), NUM_ITERATIONS, LENGTH);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] encoded = keyFactory.generateSecret(keySpec).getEncoded();

        return getArrayByteString(encoded);
    }

    private String getArrayByteString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte byteValue : bytes) {
            builder.append(Integer.toHexString(byteValue));
        }

        return builder.toString();
    }
}
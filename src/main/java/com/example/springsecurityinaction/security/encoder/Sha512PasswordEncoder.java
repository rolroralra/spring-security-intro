package com.example.springsecurityinaction.security.encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Sha512PasswordEncoder implements PasswordEncoder {
    public static final String ENCODING_ALGORITHM = "SHA-512";

    @Override
    public String encode(CharSequence rawPassword) {
        return hashWithSHA512(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashedPassword = hashWithSHA512(rawPassword);

        return encodedPassword.equals(hashedPassword);
    }

    private String hashWithSHA512(CharSequence rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ENCODING_ALGORITHM);

            byte[] digested = digest.digest(rawPassword.toString().getBytes());

            return new String(digested);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

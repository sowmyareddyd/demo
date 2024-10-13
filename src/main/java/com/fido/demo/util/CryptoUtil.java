package com.fido.demo.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class CryptoUtil {

    public String generateSecureRandomString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);  // Fill the byte array with random bytes

        // Encode the random bytes to a string using Base64 (or Hex, depending on your preference)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

}

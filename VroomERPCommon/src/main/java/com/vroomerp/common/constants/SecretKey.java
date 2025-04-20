package com.vroomerp.common.constants;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKey {
    public static void main(String[] args) {
        byte[] key = new byte[32]; // 256-bit key
        new SecureRandom().nextBytes(key);
        String base64Key = Base64.getEncoder().encodeToString(key);

        System.out.println("Nuova API_KEY_SECRET:");
        System.out.println(base64Key);
    }
}

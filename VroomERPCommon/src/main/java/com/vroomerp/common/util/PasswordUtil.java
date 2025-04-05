package com.vroomerp.common.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Codifica la password in modo sicuro
    public static String encodePassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verifica se la password è corretta (login)
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}

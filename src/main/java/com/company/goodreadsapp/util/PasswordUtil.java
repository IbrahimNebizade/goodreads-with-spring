package com.company.goodreadsapp.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {
    public static String hash(String plainPassword) {
        var salt = BCrypt.gensalt(10);
        return BCrypt.hashpw(plainPassword, salt);
    }

    public static boolean checkPassword(String plain, String password) {
        return BCrypt.checkpw(plain, password);
    }

}

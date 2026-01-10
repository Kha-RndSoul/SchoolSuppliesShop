package com.shop.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password hashing and verification using BCrypt
 */
public class PasswordUtil {

    // BCrypt work factor (số vòng lặp)
    private static final int WORK_FACTOR = 12;

    /**
     * Hash password sử dụng BCrypt
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password không được rỗng");
        }

        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(WORK_FACTOR));
    }

    /**
     * Verify password có match với hash không
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }

        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            // Nếu hashedPassword không phải BCrypt format → return false
            return false;
        }
    }

    /**
     * Check xem string có phải BCrypt hash không
     */
    public static boolean isBCryptHash(String password) {
        if (password == null) {
            return false;
        }
        // BCrypt hash bắt đầu với $2a$, $2b$, hoặc $2y$ và có độ dài 60
        return password.matches("^\\$2[aby]\\$\\d{2}\\$.{53}$");
    }
}
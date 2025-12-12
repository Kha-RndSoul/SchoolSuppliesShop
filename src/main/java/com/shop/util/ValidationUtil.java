package com.shop.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    // ==================== EMAIL VALIDATION ====================

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    /**
     * Validate email format
     * @param email Email to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email. trim()).matches();
    }

    // ==================== PHONE VALIDATION ====================

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(0|\\+84)(3[2-9]|5[689]|7[06-9]|8[1-689]|9[0-9])[0-9]{7}$"
    );

    /**
     * Validate Vietnamese phone number
     * Format: 0xxxxxxxxx or +84xxxxxxxxx
     * @param phone Phone number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        String cleanPhone = phone.trim().replaceAll("\\s+", "");
        return PHONE_PATTERN.matcher(cleanPhone).matches();
    }

    // ==================== PASSWORD VALIDATION ====================

    /**
     * Validate password strength
     * Rules:  Minimum 6 characters
     * @param password Password to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return password.length() >= 6;
    }

    /**
     * Validate strong password
     * Rules: At least 8 characters, contains uppercase, lowercase, number
     * @param password Password to validate
     * @return true if valid, false otherwise
     */
    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isLowerCase(c)) hasLower = true;
            if (Character.isDigit(c)) hasDigit = true;
        }

        return hasUpper && hasLower && hasDigit;
    }

    // ==================== STRING VALIDATION ====================

    /**
     * Check if string is null or empty
     * @param str String to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Check if string is not empty
     * @param str String to check
     * @return true if not empty, false otherwise
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Validate string length
     * @param str String to validate
     * @param minLength Minimum length
     * @param maxLength Maximum length
     * @return true if length is valid, false otherwise
     */
    public static boolean isValidLength(String str, int minLength, int maxLength) {
        if (str == null) {
            return false;
        }
        int length = str.trim().length();
        return length >= minLength && length <= maxLength;
    }

    /**
     * Validate full name
     * Rules: 2-100 characters, only letters and spaces
     * @param fullName Full name to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidFullName(String fullName) {
        if (isEmpty(fullName)) {
            return false;
        }

        String trimmed = fullName.trim();

        // Check length
        if (trimmed.length() < 2 || trimmed.length() > 100) {
            return false;
        }

        // Check contains only letters, spaces, Vietnamese characters
        return trimmed.matches("^[a-zA-ZÀ-ỹ\\s]+$");
    }

    // ==================== NUMBER VALIDATION ====================

    /**
     * Check if string is a valid integer
     * @param str String to check
     * @return true if valid integer, false otherwise
     */
    public static boolean isInteger(String str) {
        if (isEmpty(str)) {
            return false;
        }
        try {
            Integer.parseInt(str. trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check if string is a valid positive integer
     * @param str String to check
     * @return true if valid positive integer, false otherwise
     */
    public static boolean isPositiveInteger(String str) {
        if (! isInteger(str)) {
            return false;
        }
        return Integer.parseInt(str.trim()) > 0;
    }

    /**
     * Check if string is a valid decimal number
     * @param str String to check
     * @return true if valid decimal, false otherwise
     */
    public static boolean isDecimal(String str) {
        if (isEmpty(str)) {
            return false;
        }
        try {
            Double.parseDouble(str. trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validate price
     * Rules: Positive number, max 2 decimal places
     * @param price Price to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPrice(double price) {
        return price > 0 && price <= 999999999.99;
    }

    /**
     * Validate quantity
     * Rules: Positive integer, max 9999
     * @param quantity Quantity to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidQuantity(int quantity) {
        return quantity > 0 && quantity <= 9999;
    }

    // ==================== RATING VALIDATION ====================

    /**
     * Validate rating value
     * Rules: Integer from 1 to 5
     * @param rating Rating to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidRating(int rating) {
        return rating >= 1 && rating <= 5;
    }

    // ==================== USERNAME VALIDATION ====================

    /**
     * Validate username
     * Rules: 3-50 characters, alphanumeric and underscore only
     * @param username Username to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidUsername(String username) {
        if (isEmpty(username)) {
            return false;
        }

        String trimmed = username.trim();

        // Check length
        if (trimmed.length() < 3 || trimmed.length() > 50) {
            return false;
        }

        // Check format:  alphanumeric and underscore only
        return trimmed.matches("^[a-zA-Z0-9_]+$");
    }

    // ==================== URL VALIDATION ====================

    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?://)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(/.*)?$"
    );

    /**
     * Validate URL format
     * @param url URL to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidUrl(String url) {
        if (isEmpty(url)) {
            return false;
        }
        return URL_PATTERN.matcher(url.trim()).matches();
    }

    // ==================== SANITIZATION ====================

    /**
     * Remove leading/trailing whitespace and normalize internal spaces
     * @param str String to sanitize
     * @return Sanitized string
     */
    public static String sanitize(String str) {
        if (str == null) {
            return "";
        }
        return str.trim().replaceAll("\\s+", " ");
    }

    /**
     * Escape HTML special characters to prevent XSS
     * @param str String to escape
     * @return Escaped string
     */
    public static String escapeHtml(String str) {
        if (str == null) {
            return "";
        }
        return str. replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                . replace("\"", "&quot;")
                .replace("'", "&#x27;")
                .replace("/", "&#x2F;");
    }

    // ==================== COMPARISON ====================

    /**
     * Check if two strings are equal (case-insensitive, trimmed)
     * @param str1 First string
     * @param str2 Second string
     * @return true if equal, false otherwise
     */
    public static boolean equalsIgnoreCaseTrimmed(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.trim().equalsIgnoreCase(str2.trim());
    }
}
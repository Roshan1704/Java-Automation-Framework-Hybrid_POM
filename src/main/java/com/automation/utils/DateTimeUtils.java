package com.automation.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Date Time Utilities
 */
public class DateTimeUtils {

    private DateTimeUtils() {
        // Private constructor
    }

    public static String getCurrentDateTime(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String getCurrentDate(String format) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(format));
    }

    public static String getTimestamp() {
        return getCurrentDateTime("yyyyMMdd_HHmmss");
    }

    public static String getReadableDateTime() {
        return getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
    }
}

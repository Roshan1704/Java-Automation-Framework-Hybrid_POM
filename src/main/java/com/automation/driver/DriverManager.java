package com.automation.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;

/**
 * Driver Manager - Thread-safe WebDriver instance management
 * Uses ThreadLocal for parallel execution support
 */
public class DriverManager {

    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
        // Private constructor
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
        log.debug("WebDriver set for thread: {}", Thread.currentThread().getId());
    }

    public static void initDriver() {
        if (driverThreadLocal.get() == null) {
            WebDriver driver = DriverFactory.createDriver();
            setDriver(driver);
            log.info("WebDriver initialized for thread: {}", Thread.currentThread().getId());
        }
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                log.info("WebDriver quit for thread: {}", Thread.currentThread().getId());
            } catch (Exception e) {
                log.error("Error quitting WebDriver: {}", e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    public static boolean hasDriver() {
        return driverThreadLocal.get() != null;
    }
}

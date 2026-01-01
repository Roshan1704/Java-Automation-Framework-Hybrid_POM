package com.automation.utils;

import com.automation.constants.FrameworkConstants;
import com.automation.driver.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Screenshot Utility - Captures screenshots
 */
public class ScreenshotUtils {

    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtils.class);

    private ScreenshotUtils() {
        // Private constructor
    }

    public static String captureScreenshot(String testName) {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            log.warn("WebDriver is null, cannot capture screenshot");
            return null;
        }

        try {
            String timestamp = LocalDateTime.now().format(FrameworkConstants.SCREENSHOT_DATE_FORMAT);
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = FrameworkConstants.SCREENSHOTS_PATH + fileName;

            // Create directory if not exists
            new File(FrameworkConstants.SCREENSHOTS_PATH).mkdirs();

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            FileUtils.copyFile(srcFile, destFile);

            log.info("Screenshot captured: {}", filePath);
            return filePath;
        } catch (IOException e) {
            log.error("Failed to capture screenshot: {}", e.getMessage());
            return null;
        }
    }

    public static byte[] captureScreenshotAsBytes() {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            log.warn("WebDriver is null, cannot capture screenshot");
            return null;
        }

        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            log.error("Failed to capture screenshot as bytes: {}", e.getMessage());
            return null;
        }
    }

    public static String captureScreenshotAsBase64() {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            log.warn("WebDriver is null, cannot capture screenshot");
            return null;
        }

        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            log.error("Failed to capture screenshot as Base64: {}", e.getMessage());
            return null;
        }
    }
}

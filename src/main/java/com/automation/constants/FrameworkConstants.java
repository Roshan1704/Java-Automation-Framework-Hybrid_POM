package com.automation.constants;

import java.time.format.DateTimeFormatter;

/**
 * Framework Constants - Centralized location for all framework constants
 */
public final class FrameworkConstants {

    private FrameworkConstants() {
        // Private constructor to prevent instantiation
    }

    // Directories
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String RESOURCES_PATH = PROJECT_PATH + "/src/test/resources/";
    public static final String CONFIG_PATH = RESOURCES_PATH + "config/";
    public static final String TEST_DATA_PATH = RESOURCES_PATH + "testdata/";
    public static final String SCREENSHOTS_PATH = PROJECT_PATH + "/target/screenshots/";
    public static final String EXTENT_REPORT_PATH = PROJECT_PATH + "/target/extent-reports/";
    public static final String ALLURE_RESULTS_PATH = PROJECT_PATH + "/target/allure-results/";

    // Files
    public static final String CONFIG_FILE = "config.properties";
    public static final String LOG4J_CONFIG = "log4j2.xml";
    public static final String EXCEL_DATA_FILE = "testdata.xlsx";
    public static final String JSON_DATA_FILE = "testdata.json";

    // Timeouts (in seconds)
    public static final int DEFAULT_IMPLICIT_WAIT = 10;
    public static final int DEFAULT_EXPLICIT_WAIT = 20;
    public static final int DEFAULT_PAGE_LOAD_TIMEOUT = 30;
    public static final int DEFAULT_SCRIPT_TIMEOUT = 30;
    public static final int POLLING_INTERVAL = 500; // milliseconds

    // Report Settings
    public static final String EXTENT_REPORT_NAME = "Automation Test Report";
    public static final String EXTENT_REPORT_TITLE = "Test Execution Report";
    public static final DateTimeFormatter REPORT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    public static final DateTimeFormatter SCREENSHOT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    // Browser Options
    public static final String[] CHROME_OPTIONS = {
            "--disable-notifications",
            "--disable-popup-blocking",
            "--disable-infobars",
            "--start-maximized",
            "--disable-extensions",
            "--no-sandbox",
            "--disable-dev-shm-usage",
            "--disable-gpu",
            "--ignore-certificate-errors"
    };

    public static final String[] FIREFOX_OPTIONS = {
            "-private"
    };

    // API Testing
    public static final int API_TIMEOUT = 30000;
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_XML = "application/xml";

    // Test Categories
    public static final String SMOKE = "smoke";
    public static final String REGRESSION = "regression";
    public static final String SANITY = "sanity";
    public static final String E2E = "e2e";
    public static final String API = "api";
    public static final String PERFORMANCE = "performance";

    // Retry Configuration
    public static final int MAX_RETRY_COUNT = 2;
}

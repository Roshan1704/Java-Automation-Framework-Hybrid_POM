package com.automation.reports;

import com.automation.config.ConfigManager;
import com.automation.constants.FrameworkConstants;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;

/**
 * Extent Report Manager - Manages ExtentReports instance and tests
 */
public class ExtentManager {

    private static final Logger log = LoggerFactory.getLogger(ExtentManager.class);

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static String reportPath;

    private ExtentManager() {
        // Private constructor
    }

    public static void initReport() {
        if (extent == null) {
            String timestamp = LocalDateTime.now().format(FrameworkConstants.REPORT_DATE_FORMAT);
            reportPath = FrameworkConstants.EXTENT_REPORT_PATH + "TestReport_" + timestamp + ".html";

            // Create directory if not exists
            new File(FrameworkConstants.EXTENT_REPORT_PATH).mkdirs();

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle(FrameworkConstants.EXTENT_REPORT_TITLE);
            sparkReporter.config().setReportName(FrameworkConstants.EXTENT_REPORT_NAME);
            sparkReporter.config().setEncoding("UTF-8");
            sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Environment", ConfigManager.getInstance().getEnvironment());
            extent.setSystemInfo("Browser", ConfigManager.getInstance().getBrowser());
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));

            log.info("Extent Report initialized at: {}", reportPath);
        }
    }

    public static void createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);
        log.debug("Created test in report: {}", testName);
    }

    public static void createTest(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        extentTest.set(test);
        log.debug("Created test in report: {} - {}", testName, description);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            log.info("Extent Report flushed to: {}", reportPath);
        }
    }

    public static void removeTest() {
        extentTest.remove();
    }

    public static String getReportPath() {
        return reportPath;
    }

    // Logging methods
    public static void logPass(String message) {
        if (getTest() != null) {
            getTest().pass(message);
        }
    }

    public static void logFail(String message) {
        if (getTest() != null) {
            getTest().fail(message);
        }
    }

    public static void logSkip(String message) {
        if (getTest() != null) {
            getTest().skip(message);
        }
    }

    public static void logInfo(String message) {
        if (getTest() != null) {
            getTest().info(message);
        }
    }

    public static void logWarning(String message) {
        if (getTest() != null) {
            getTest().warning(message);
        }
    }

    public static void assignCategory(String... categories) {
        if (getTest() != null) {
            getTest().assignCategory(categories);
        }
    }

    public static void assignAuthor(String... authors) {
        if (getTest() != null) {
            getTest().assignAuthor(authors);
        }
    }

    public static void assignDevice(String device) {
        if (getTest() != null) {
            getTest().assignDevice(device);
        }
    }
}

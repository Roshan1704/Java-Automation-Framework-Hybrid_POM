package com.automation.base;

import com.automation.config.ConfigManager;
import com.automation.driver.DriverManager;
import com.automation.reports.AllureManager;
import com.automation.reports.ExtentManager;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

/**
 * Base Test - Parent class for all test classes
 */
public abstract class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        log.info("========== Test Suite Starting ==========");
        ExtentManager.initReport();
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        log.info("Test Class: {}", this.getClass().getSimpleName());
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestResult result) {
        log.info(">>> Starting Test: {}", result.getMethod().getMethodName());

        // Initialize WebDriver
        DriverManager.initDriver();
        driver = DriverManager.getDriver();

        // Navigate to base URL
        String baseUrl = ConfigManager.getInstance().getBaseUrl();
        if (baseUrl != null && !baseUrl.isEmpty()) {
            driver.get(baseUrl);
            log.info("Navigated to: {}", baseUrl);
        }

        // Add test info to Allure
        Allure.parameter("Browser", ConfigManager.getInstance().getBrowser());
        Allure.parameter("Environment", ConfigManager.getInstance().getEnvironment());
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                log.info("✓ Test Passed: {}", testName);
                break;
            case ITestResult.FAILURE:
                log.error("✗ Test Failed: {}", testName);
                break;
            case ITestResult.SKIP:
                log.warn("⊘ Test Skipped: {}", testName);
                break;
        }

        // Quit driver
        DriverManager.quitDriver();
        log.info("<<< Finished Test: {}", testName);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        log.info("Test Class Completed: {}", this.getClass().getSimpleName());
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        ExtentManager.flushReport();
        log.info("========== Test Suite Completed ==========");
        log.info("Extent Report: {}", ExtentManager.getReportPath());
    }
}

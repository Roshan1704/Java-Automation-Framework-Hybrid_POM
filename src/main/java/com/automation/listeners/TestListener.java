package com.automation.listeners;

import com.automation.config.ConfigManager;
import com.automation.reports.AllureManager;
import com.automation.reports.ExtentManager;
import com.automation.utils.ScreenshotUtils;
import com.automation.utils.SlackNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;

/**
 * TestNG Test Listener - Handles test events for reporting
 */
public class TestListener implements ITestListener, ISuiteListener {

    private static final Logger log = LoggerFactory.getLogger(TestListener.class);

    private int passedCount = 0;
    private int failedCount = 0;
    private int skippedCount = 0;

    @Override
    public void onStart(ISuite suite) {
        log.info("========== Suite Started: {} ==========", suite.getName());
        ExtentManager.initReport();
    }

    @Override
    public void onFinish(ISuite suite) {
        log.info("========== Suite Finished: {} ==========", suite.getName());
        ExtentManager.flushReport();

        // Send notifications
        if (ConfigManager.getInstance().isSlackNotificationEnabled()) {
            sendSlackNotification(suite.getName());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = getTestName(result);
        log.info(">>> Test Started: {}", testName);
        ExtentManager.createTest(testName, result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = getTestName(result);
        log.info("✓ Test Passed: {}", testName);
        ExtentManager.logPass("Test passed successfully");
        passedCount++;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = getTestName(result);
        log.error("✗ Test Failed: {}", testName);
        log.error("Failure reason: {}", result.getThrowable().getMessage());

        // Capture and attach screenshot
        byte[] screenshot = ScreenshotUtils.captureScreenshotAsBytes();
        if (screenshot != null) {
            String screenshotPath = ScreenshotUtils.captureScreenshot(testName);
            ExtentManager.logFail("Test failed - Screenshot: " + screenshotPath);
            AllureManager.attachScreenshot("Failure Screenshot", screenshot);
        }

        ExtentManager.logFail("Error: " + result.getThrowable().getMessage());
        failedCount++;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = getTestName(result);
        log.warn("⊘ Test Skipped: {}", testName);
        ExtentManager.logSkip("Test skipped: " + result.getThrowable().getMessage());
        skippedCount++;
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.warn("Test failed but within success percentage: {}", getTestName(result));
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        log.error("Test failed with timeout: {}", getTestName(result));
        onTestFailure(result);
    }

    private String getTestName(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
    }

    private void sendSlackNotification(String suiteName) {
        try {
            int totalTests = passedCount + failedCount + skippedCount;
            String message = String.format(
                    "🔔 *Test Execution Complete*\n" +
                    "Suite: %s\n" +
                    "Total: %d | ✅ Passed: %d | ❌ Failed: %d | ⏭ Skipped: %d\n" +
                    "Pass Rate: %.2f%%",
                    suiteName, totalTests, passedCount, failedCount, skippedCount,
                    totalTests > 0 ? (passedCount * 100.0 / totalTests) : 0
            );
            SlackNotifier.sendNotification(message);
            log.info("Slack notification sent");
        } catch (Exception e) {
            log.error("Failed to send Slack notification: {}", e.getMessage());
        }
    }
}

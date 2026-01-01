package com.automation.listeners;

import com.automation.reports.AllureManager;
import com.automation.utils.ScreenshotUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Allure Listener - Handles Allure specific reporting
 */
public class AllureListener implements ITestListener {

    private static final Logger log = LoggerFactory.getLogger(AllureListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        byte[] screenshot = ScreenshotUtils.captureScreenshotAsBytes();
        if (screenshot != null) {
            AllureManager.attachScreenshot("Failure Screenshot", screenshot);
        }
        AllureManager.attachText("Error Details", result.getThrowable().toString());
        log.debug("Allure attachments added for failed test: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.getThrowable() != null) {
            AllureManager.attachText("Skip Reason", result.getThrowable().toString());
        }
    }
}

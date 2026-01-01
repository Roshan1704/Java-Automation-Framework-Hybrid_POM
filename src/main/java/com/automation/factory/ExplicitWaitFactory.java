package com.automation.factory;

import com.automation.config.ConfigManager;
import com.automation.driver.DriverManager;
import com.automation.enums.WaitStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Explicit Wait Factory - Provides various wait strategies
 */
public class ExplicitWaitFactory {

    private static final Logger log = LoggerFactory.getLogger(ExplicitWaitFactory.class);

    private ExplicitWaitFactory() {
        // Private constructor
    }

    private static WebDriverWait getWait() {
        int timeout = ConfigManager.getInstance().getExplicitWait();
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
    }

    private static WebDriverWait getWait(int timeoutSeconds) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSeconds));
    }

    public static WebElement performExplicitWait(WaitStrategy strategy, By locator) {
        WebElement element = null;
        WebDriverWait wait = getWait();

        switch (strategy) {
            case CLICKABLE:
                element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                break;
            case VISIBLE:
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                break;
            case PRESENCE:
                element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                break;
            case NONE:
                element = DriverManager.getDriver().findElement(locator);
                break;
            default:
                log.warn("Unknown wait strategy: {}", strategy);
        }

        return element;
    }

    public static WebElement waitForClickable(By locator) {
        return getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForVisible(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForPresence(By locator) {
        return getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static List<WebElement> waitForAllVisible(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static List<WebElement> waitForAllPresent(By locator) {
        return getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public static boolean waitForInvisibility(By locator) {
        return getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitForFrame(By locator) {
        getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    public static void waitForFrame(String nameOrId) {
        getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
    }

    public static void waitForAlert() {
        getWait().until(ExpectedConditions.alertIsPresent());
    }

    public static boolean waitForTextPresent(By locator, String text) {
        return getWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static boolean waitForUrl(String url) {
        return getWait().until(ExpectedConditions.urlContains(url));
    }

    public static boolean waitForTitleContains(String title) {
        return getWait().until(ExpectedConditions.titleContains(title));
    }

    public static WebElement waitWithCustomTimeout(By locator, int timeoutSeconds) {
        return getWait(timeoutSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}

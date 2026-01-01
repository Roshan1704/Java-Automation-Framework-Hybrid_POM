package com.automation.pages;

import com.automation.driver.DriverManager;
import com.automation.enums.WaitStrategy;
import com.automation.factory.ExplicitWaitFactory;
import com.automation.utils.ScreenshotUtils;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Set;

/**
 * Base Page - Parent class for all page objects
 * Contains common methods used across pages
 */
public abstract class BasePage {

    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    protected WebDriver driver;
    protected Actions actions;
    protected JavascriptExecutor jsExecutor;

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.actions = new Actions(driver);
        this.jsExecutor = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // Navigation Methods
    @Step("Navigate to URL: {url}")
    protected void navigateTo(String url) {
        driver.get(url);
        log.info("Navigated to: {}", url);
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void refreshPage() {
        driver.navigate().refresh();
        log.info("Page refreshed");
    }

    protected void navigateBack() {
        driver.navigate().back();
        log.info("Navigated back");
    }

    protected void navigateForward() {
        driver.navigate().forward();
        log.info("Navigated forward");
    }

    // Element Interaction Methods
    protected void click(By locator, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
        element.click();
        log.debug("Clicked on element: {}", locator);
    }

    protected void click(By locator) {
        click(locator, WaitStrategy.CLICKABLE);
    }

    protected void sendKeys(By locator, String text, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
        element.clear();
        element.sendKeys(text);
        log.debug("Entered text '{}' in element: {}", text, locator);
    }

    protected void sendKeys(By locator, String text) {
        sendKeys(locator, text, WaitStrategy.VISIBLE);
    }

    protected void clearAndType(By locator, String text) {
        WebElement element = ExplicitWaitFactory.waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
        log.debug("Cleared and entered text '{}' in element: {}", text, locator);
    }

    protected String getText(By locator, WaitStrategy waitStrategy) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitStrategy, locator);
        String text = element.getText();
        log.debug("Got text '{}' from element: {}", text, locator);
        return text;
    }

    protected String getText(By locator) {
        return getText(locator, WaitStrategy.VISIBLE);
    }

    protected String getAttribute(By locator, String attribute) {
        WebElement element = ExplicitWaitFactory.waitForPresence(locator);
        return element.getAttribute(attribute);
    }

    protected boolean isDisplayed(By locator) {
        try {
            return ExplicitWaitFactory.waitForVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isEnabled(By locator) {
        return ExplicitWaitFactory.waitForPresence(locator).isEnabled();
    }

    protected boolean isSelected(By locator) {
        return ExplicitWaitFactory.waitForPresence(locator).isSelected();
    }

    // Dropdown Methods
    protected void selectByVisibleText(By locator, String text) {
        Select select = new Select(ExplicitWaitFactory.waitForVisible(locator));
        select.selectByVisibleText(text);
        log.debug("Selected '{}' from dropdown: {}", text, locator);
    }

    protected void selectByValue(By locator, String value) {
        Select select = new Select(ExplicitWaitFactory.waitForVisible(locator));
        select.selectByValue(value);
        log.debug("Selected value '{}' from dropdown: {}", value, locator);
    }

    protected void selectByIndex(By locator, int index) {
        Select select = new Select(ExplicitWaitFactory.waitForVisible(locator));
        select.selectByIndex(index);
        log.debug("Selected index '{}' from dropdown: {}", index, locator);
    }

    protected String getSelectedText(By locator) {
        Select select = new Select(ExplicitWaitFactory.waitForVisible(locator));
        return select.getFirstSelectedOption().getText();
    }

    // Actions Methods
    protected void hoverOver(By locator) {
        WebElement element = ExplicitWaitFactory.waitForVisible(locator);
        actions.moveToElement(element).perform();
        log.debug("Hovered over element: {}", locator);
    }

    protected void doubleClick(By locator) {
        WebElement element = ExplicitWaitFactory.waitForClickable(locator);
        actions.doubleClick(element).perform();
        log.debug("Double clicked on element: {}", locator);
    }

    protected void rightClick(By locator) {
        WebElement element = ExplicitWaitFactory.waitForClickable(locator);
        actions.contextClick(element).perform();
        log.debug("Right clicked on element: {}", locator);
    }

    protected void dragAndDrop(By source, By target) {
        WebElement sourceElement = ExplicitWaitFactory.waitForVisible(source);
        WebElement targetElement = ExplicitWaitFactory.waitForVisible(target);
        actions.dragAndDrop(sourceElement, targetElement).perform();
        log.debug("Drag and drop from {} to {}", source, target);
    }

    // JavaScript Methods
    protected void jsClick(By locator) {
        WebElement element = ExplicitWaitFactory.waitForPresence(locator);
        jsExecutor.executeScript("arguments[0].click();", element);
        log.debug("JS clicked on element: {}", locator);
    }

    protected void jsScrollToElement(By locator) {
        WebElement element = ExplicitWaitFactory.waitForPresence(locator);
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        log.debug("Scrolled to element: {}", locator);
    }

    protected void jsScrollToBottom() {
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        log.debug("Scrolled to bottom of page");
    }

    protected void jsScrollToTop() {
        jsExecutor.executeScript("window.scrollTo(0, 0)");
        log.debug("Scrolled to top of page");
    }

    protected void jsHighlightElement(By locator) {
        WebElement element = ExplicitWaitFactory.waitForPresence(locator);
        jsExecutor.executeScript("arguments[0].style.border='3px solid red'", element);
    }

    protected Object executeScript(String script, Object... args) {
        return jsExecutor.executeScript(script, args);
    }

    // Frame Handling
    protected void switchToFrame(By locator) {
        ExplicitWaitFactory.waitForFrame(locator);
        log.debug("Switched to frame: {}", locator);
    }

    protected void switchToFrame(String nameOrId) {
        ExplicitWaitFactory.waitForFrame(nameOrId);
        log.debug("Switched to frame: {}", nameOrId);
    }

    protected void switchToFrame(int index) {
        driver.switchTo().frame(index);
        log.debug("Switched to frame index: {}", index);
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        log.debug("Switched to default content");
    }

    protected void switchToParentFrame() {
        driver.switchTo().parentFrame();
        log.debug("Switched to parent frame");
    }

    // Window Handling
    protected String getWindowHandle() {
        return driver.getWindowHandle();
    }

    protected Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    protected void switchToWindow(String handle) {
        driver.switchTo().window(handle);
        log.debug("Switched to window: {}", handle);
    }

    protected void switchToNewWindow() {
        String currentWindow = getWindowHandle();
        Set<String> handles = getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(currentWindow)) {
                switchToWindow(handle);
                break;
            }
        }
    }

    protected void closeCurrentWindow() {
        driver.close();
        log.debug("Closed current window");
    }

    // Alert Handling
    protected void acceptAlert() {
        ExplicitWaitFactory.waitForAlert();
        driver.switchTo().alert().accept();
        log.debug("Alert accepted");
    }

    protected void dismissAlert() {
        ExplicitWaitFactory.waitForAlert();
        driver.switchTo().alert().dismiss();
        log.debug("Alert dismissed");
    }

    protected String getAlertText() {
        ExplicitWaitFactory.waitForAlert();
        return driver.switchTo().alert().getText();
    }

    protected void sendKeysToAlert(String text) {
        ExplicitWaitFactory.waitForAlert();
        driver.switchTo().alert().sendKeys(text);
        log.debug("Entered text to alert: {}", text);
    }

    // Utility Methods
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    protected WebElement findElement(By locator) {
        return ExplicitWaitFactory.waitForPresence(locator);
    }

    protected int getElementCount(By locator) {
        return findElements(locator).size();
    }

    protected void waitForPageLoad() {
        jsExecutor.executeScript("return document.readyState").equals("complete");
        log.debug("Page load complete");
    }

    protected byte[] takeScreenshot() {
        return ScreenshotUtils.captureScreenshotAsBytes();
    }
}

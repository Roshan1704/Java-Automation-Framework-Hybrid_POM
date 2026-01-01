package com.automation.pages;

import com.automation.config.ConfigManager;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;

/**
 * Home Page - Page Object for Home/Dashboard functionality
 */
public class HomePage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    // Locators
    private final By welcomeMessage = By.className("welcome-message");
    private final By userProfileIcon = By.id("user-profile");
    private final By logoutButton = By.id("logout");
    private final By searchBox = By.id("search");
    private final By navigationMenu = By.id("nav-menu");
    private final By dashboardLink = By.linkText("Dashboard");
    private final By settingsLink = By.linkText("Settings");
    private final By notificationBell = By.id("notifications");
    private final By notificationCount = By.className("notification-count");

    public HomePage() {
        super();
    }

    @Step("Open Home Page")
    public HomePage openHomePage() {
        navigateTo(ConfigManager.getInstance().getBaseUrl());
        log.info("Home page opened");
        return this;
    }

    @Step("Get welcome message")
    public String getWelcomeMessage() {
        String message = getText(welcomeMessage);
        log.info("Welcome message: {}", message);
        return message;
    }

    public boolean isHomePageDisplayed() {
        return isDisplayed(welcomeMessage) || isDisplayed(userProfileIcon);
    }

    @Step("Click User Profile icon")
    public HomePage clickUserProfile() {
        click(userProfileIcon);
        log.info("User profile clicked");
        return this;
    }

    @Step("Click Logout button")
    public LoginPage logout() {
        click(userProfileIcon);
        click(logoutButton);
        log.info("Logged out successfully");
        return new LoginPage();
    }

    @Step("Search for: {searchTerm}")
    public HomePage search(String searchTerm) {
        sendKeys(searchBox, searchTerm);
        findElement(searchBox).submit();
        log.info("Searched for: {}", searchTerm);
        return this;
    }

    @Step("Navigate to Dashboard")
    public HomePage goToDashboard() {
        click(dashboardLink);
        log.info("Navigated to Dashboard");
        return this;
    }

    @Step("Navigate to Settings")
    public HomePage goToSettings() {
        click(settingsLink);
        log.info("Navigated to Settings");
        return this;
    }

    @Step("Click Notification bell")
    public HomePage clickNotifications() {
        click(notificationBell);
        log.info("Notifications clicked");
        return this;
    }

    public int getNotificationCount() {
        try {
            String count = getText(notificationCount);
            return Integer.parseInt(count);
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isUserLoggedIn() {
        return isDisplayed(userProfileIcon);
    }

    public String getCurrentPageTitle() {
        return getPageTitle();
    }
}

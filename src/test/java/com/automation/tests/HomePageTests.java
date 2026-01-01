package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.constants.FrameworkConstants;
import com.automation.pages.HomePage;
import com.automation.pages.LoginPage;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Home Page Tests - Test cases for Home/Dashboard functionality
 */
@Epic("Dashboard")
@Feature("Home Page")
public class HomePageTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(HomePageTests.class);

    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void setupHomePage() {
        // Login first
        LoginPage loginPage = new LoginPage();
        homePage = loginPage
                .openLoginPage()
                .login("testuser", "password123");
    }

    @Test(priority = 1, groups = {FrameworkConstants.SMOKE, FrameworkConstants.REGRESSION})
    @Story("Home Page Display")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify home page is displayed after login")
    public void testHomePageDisplay() {
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page should be displayed");
        log.info("Home page display test completed");
    }

    @Test(priority = 2, groups = {FrameworkConstants.REGRESSION})
    @Story("Welcome Message")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify welcome message is displayed")
    public void testWelcomeMessage() {
        String welcomeMessage = homePage.getWelcomeMessage();
        Assert.assertNotNull(welcomeMessage, "Welcome message should not be null");
        Assert.assertFalse(welcomeMessage.isEmpty(), "Welcome message should not be empty");
        log.info("Welcome message test completed: {}", welcomeMessage);
    }

    @Test(priority = 3, groups = {FrameworkConstants.REGRESSION})
    @Story("Search Functionality")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify search functionality works")
    public void testSearchFunctionality() {
        homePage.search("test query");
        // Add assertions based on expected search behavior
        log.info("Search functionality test completed");
    }

    @Test(priority = 4, groups = {FrameworkConstants.SMOKE})
    @Story("Logout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can logout successfully")
    public void testLogout() {
        LoginPage loginPage = homePage.logout();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed after logout");
        log.info("Logout test completed successfully");
    }

    @Test(priority = 5, groups = {FrameworkConstants.REGRESSION})
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify navigation to Dashboard")
    public void testDashboardNavigation() {
        homePage.goToDashboard();
        // Add assertions based on expected dashboard navigation
        log.info("Dashboard navigation test completed");
    }

    @Test(priority = 6, groups = {FrameworkConstants.REGRESSION})
    @Story("Notifications")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify notifications can be accessed")
    public void testNotifications() {
        homePage.clickNotifications();
        int count = homePage.getNotificationCount();
        log.info("Notification count: {}", count);
    }
}

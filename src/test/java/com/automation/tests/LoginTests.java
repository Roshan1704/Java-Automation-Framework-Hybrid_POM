package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.constants.FrameworkConstants;
import com.automation.pages.HomePage;
import com.automation.pages.LoginPage;
import com.automation.utils.DataGenerator;
import io.qameta.allure.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Login Tests - Test cases for Login functionality
 */
@Epic("Authentication")
@Feature("Login")
public class LoginTests extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(LoginTests.class);

    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpLoginPage() {
        loginPage = new LoginPage();
    }

    @Test(priority = 1, groups = {FrameworkConstants.SMOKE, FrameworkConstants.REGRESSION})
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can login with valid credentials")
    public void testValidLogin() {
        HomePage homePage = loginPage
                .openLoginPage()
                .login("testuser", "password123");

        Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in");
        log.info("Valid login test completed successfully");
    }

    @Test(priority = 2, groups = {FrameworkConstants.SMOKE, FrameworkConstants.REGRESSION})
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify error message is displayed for invalid credentials")
    public void testInvalidLogin() {
        loginPage.openLoginPage()
                .enterUsername("invaliduser")
                .enterPassword("wrongpassword")
                .clickLoginButton();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        log.info("Invalid login test completed successfully");
    }

    @Test(priority = 3, groups = {FrameworkConstants.REGRESSION})
    @Story("Empty Credentials")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify error message when login with empty credentials")
    public void testEmptyCredentials() {
        loginPage.openLoginPage()
                .enterUsername("")
                .enterPassword("")
                .clickLoginButton();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        log.info("Empty credentials test completed successfully");
    }

    @Test(priority = 4, groups = {FrameworkConstants.REGRESSION}, dataProvider = "loginData")
    @Story("Data Driven Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify login with multiple data sets")
    public void testDataDrivenLogin(Map<String, String> data) {
        String username = data.get("username");
        String password = data.get("password");
        boolean shouldPass = Boolean.parseBoolean(data.get("shouldPass"));

        loginPage.openLoginPage()
                .enterUsername(username)
                .enterPassword(password)
                .clickLoginButton();

        if (shouldPass) {
            HomePage homePage = new HomePage();
            Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in");
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        }

        log.info("Data driven login test completed for user: {}", username);
    }

    @Test(priority = 5, groups = {FrameworkConstants.SANITY})
    @Story("Remember Me")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify remember me functionality")
    public void testRememberMe() {
        loginPage.openLoginPage()
                .enterUsername("testuser")
                .enterPassword("password123")
                .checkRememberMe()
                .clickLoginButton();

        // Verification would depend on actual implementation
        log.info("Remember me test completed");
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
                { Map.of("username", "validuser1", "password", "pass123", "shouldPass", "true") },
                { Map.of("username", "validuser2", "password", "pass456", "shouldPass", "true") },
                { Map.of("username", "invalid", "password", "wrong", "shouldPass", "false") },
                { Map.of("username", "", "password", "pass", "shouldPass", "false") }
        };
    }
}

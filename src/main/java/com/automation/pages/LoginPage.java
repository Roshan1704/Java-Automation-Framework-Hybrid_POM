package com.automation.pages;

import com.automation.config.ConfigManager;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;

/**
 * Login Page - Page Object for Login functionality
 */
public class LoginPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

    // Locators
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.className("error-message");
    private final By rememberMeCheckbox = By.id("remember-me");
    private final By forgotPasswordLink = By.linkText("Forgot Password?");
    private final By signUpLink = By.linkText("Sign Up");

    public LoginPage() {
        super();
    }

    @Step("Open Login Page")
    public LoginPage openLoginPage() {
        navigateTo(ConfigManager.getInstance().getBaseUrl() + "/login");
        log.info("Login page opened");
        return this;
    }

    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username) {
        sendKeys(usernameField, username);
        log.info("Username entered: {}", username);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        sendKeys(passwordField, password);
        log.info("Password entered");
        return this;
    }

    @Step("Click Login button")
    public HomePage clickLoginButton() {
        click(loginButton);
        log.info("Login button clicked");
        return new HomePage();
    }

    @Step("Login with credentials: {username}")
    public HomePage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLoginButton();
    }

    @Step("Check Remember Me checkbox")
    public LoginPage checkRememberMe() {
        if (!isSelected(rememberMeCheckbox)) {
            click(rememberMeCheckbox);
            log.info("Remember Me checkbox checked");
        }
        return this;
    }

    @Step("Click Forgot Password link")
    public void clickForgotPassword() {
        click(forgotPasswordLink);
        log.info("Forgot Password link clicked");
    }

    @Step("Click Sign Up link")
    public void clickSignUp() {
        click(signUpLink);
        log.info("Sign Up link clicked");
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public boolean isLoginPageDisplayed() {
        return isDisplayed(usernameField) && isDisplayed(passwordField);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}

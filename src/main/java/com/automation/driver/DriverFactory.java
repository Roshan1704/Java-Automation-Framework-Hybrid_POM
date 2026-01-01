package com.automation.driver;

import com.automation.config.ConfigManager;
import com.automation.constants.FrameworkConstants;
import com.automation.enums.BrowserType;
import com.automation.exceptions.FrameworkException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Driver Factory - Creates and configures WebDriver instances
 * Supports Chrome, Firefox, Edge with local and remote execution
 */
public class DriverFactory {

    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);

    private DriverFactory() {
        // Private constructor
    }

    public static WebDriver createDriver() {
        ConfigManager config = ConfigManager.getInstance();
        String browserName = config.getBrowser();
        BrowserType browserType = BrowserType.valueOf(browserName.toUpperCase());

        WebDriver driver;

        if (config.isRemoteExecution()) {
            driver = createRemoteDriver(browserType, config);
        } else {
            driver = createLocalDriver(browserType, config);
        }

        configureDriver(driver, config);
        return driver;
    }

    private static WebDriver createLocalDriver(BrowserType browserType, ConfigManager config) {
        WebDriver driver;

        switch (browserType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(getChromeOptions(config));
                log.info("Chrome browser initialized");
                break;

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(getFirefoxOptions(config));
                log.info("Firefox browser initialized");
                break;

            case EDGE:
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver(getEdgeOptions(config));
                log.info("Edge browser initialized");
                break;

            default:
                throw new FrameworkException("Unsupported browser type: " + browserType);
        }

        return driver;
    }

    private static WebDriver createRemoteDriver(BrowserType browserType, ConfigManager config) {
        WebDriver driver;
        String remoteUrl = config.getRemoteUrl();

        try {
            switch (browserType) {
                case CHROME:
                    driver = new RemoteWebDriver(new URL(remoteUrl), getChromeOptions(config));
                    break;

                case FIREFOX:
                    driver = new RemoteWebDriver(new URL(remoteUrl), getFirefoxOptions(config));
                    break;

                case EDGE:
                    driver = new RemoteWebDriver(new URL(remoteUrl), getEdgeOptions(config));
                    break;

                default:
                    throw new FrameworkException("Unsupported browser type for remote: " + browserType);
            }

            log.info("Remote {} browser initialized at {}", browserType, remoteUrl);
        } catch (MalformedURLException e) {
            throw new FrameworkException("Invalid remote URL: " + remoteUrl, e);
        }

        return driver;
    }

    private static ChromeOptions getChromeOptions(ConfigManager config) {
        ChromeOptions options = new ChromeOptions();

        for (String arg : FrameworkConstants.CHROME_OPTIONS) {
            options.addArguments(arg);
        }

        if (config.isHeadless()) {
            options.addArguments("--headless=new");
            log.info("Chrome running in headless mode");
        }

        options.setAcceptInsecureCerts(true);
        return options;
    }

    private static FirefoxOptions getFirefoxOptions(ConfigManager config) {
        FirefoxOptions options = new FirefoxOptions();

        for (String arg : FrameworkConstants.FIREFOX_OPTIONS) {
            options.addArguments(arg);
        }

        if (config.isHeadless()) {
            options.addArguments("-headless");
            log.info("Firefox running in headless mode");
        }

        options.setAcceptInsecureCerts(true);
        return options;
    }

    private static EdgeOptions getEdgeOptions(ConfigManager config) {
        EdgeOptions options = new EdgeOptions();

        if (config.isHeadless()) {
            options.addArguments("--headless=new");
            log.info("Edge running in headless mode");
        }

        options.setAcceptInsecureCerts(true);
        return options;
    }

    private static void configureDriver(WebDriver driver, ConfigManager config) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(FrameworkConstants.DEFAULT_SCRIPT_TIMEOUT));
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        log.info("Driver configured with implicit wait: {}s, page load timeout: {}s",
                config.getImplicitWait(), config.getPageLoadTimeout());
    }
}

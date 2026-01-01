package com.automation.config;

import com.automation.enums.EnvironmentType;
import com.automation.exceptions.InvalidConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Manager - Singleton pattern for managing application configurations
 * Supports multiple environments: dev, staging, prod
 */
public class ConfigManager {

    private static final Logger log = LoggerFactory.getLogger(ConfigManager.class);

    private static ConfigManager instance;
    private final Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config/";

    private ConfigManager() {
        properties = new Properties();
        loadConfig();
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadConfig() {
        try {
            // Load default config
            FileInputStream defaultConfig = new FileInputStream(CONFIG_PATH + "config.properties");
            properties.load(defaultConfig);
            defaultConfig.close();

            // Load environment-specific config
            String env = System.getProperty("env", properties.getProperty("default.environment", "dev"));
            FileInputStream envConfig = new FileInputStream(CONFIG_PATH + env + ".properties");
            properties.load(envConfig);
            envConfig.close();

            log.info("Configuration loaded for environment: {}", env);
        } catch (IOException e) {
            log.error("Failed to load configuration: {}", e.getMessage());
            throw new InvalidConfigException("Configuration loading failed", e);
        }
    }

    public String getProperty(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }

    public int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    // Convenience methods
    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public boolean isHeadless() {
        return getBooleanProperty("headless", false);
    }

    public int getImplicitWait() {
        return getIntProperty("implicit.wait", 10);
    }

    public int getExplicitWait() {
        return getIntProperty("explicit.wait", 20);
    }

    public int getPageLoadTimeout() {
        return getIntProperty("page.load.timeout", 30);
    }

    public String getEnvironment() {
        return getProperty("environment", "dev");
    }

    public EnvironmentType getEnvironmentType() {
        return EnvironmentType.valueOf(getEnvironment().toUpperCase());
    }

    public boolean isRemoteExecution() {
        return getBooleanProperty("remote.execution", false);
    }

    public String getRemoteUrl() {
        return getProperty("remote.url", "http://localhost:4444/wd/hub");
    }

    public String getSlackWebhookUrl() {
        return getProperty("slack.webhook.url");
    }

    public boolean isSlackNotificationEnabled() {
        return getBooleanProperty("slack.notification.enabled", false);
    }

    public String getApiBaseUrl() {
        return getProperty("api.base.url");
    }

    public void reload() {
        properties.clear();
        loadConfig();
    }
}

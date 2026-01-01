package com.automation.enums;

/**
 * Supported environment types
 */
public enum EnvironmentType {
    DEV("dev"),
    STAGING("staging"),
    PROD("prod"),
    QA("qa");

    private final String envName;

    EnvironmentType(String envName) {
        this.envName = envName;
    }

    public String getEnvName() {
        return envName;
    }
}

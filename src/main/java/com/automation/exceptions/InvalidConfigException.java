package com.automation.exceptions;

/**
 * Exception for invalid configuration
 */
public class InvalidConfigException extends FrameworkException {

    public InvalidConfigException(String message) {
        super(message);
    }

    public InvalidConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.automation.reports;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

/**
 * Allure Report Manager - Utility methods for Allure reporting
 */
public class AllureManager {

    private static final Logger log = LoggerFactory.getLogger(AllureManager.class);

    private AllureManager() {
        // Private constructor
    }

    public static void attachScreenshot(String name, byte[] screenshot) {
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
        log.debug("Screenshot attached to Allure report: {}", name);
    }

    public static void attachText(String name, String content) {
        Allure.addAttachment(name, "text/plain", content);
        log.debug("Text attached to Allure report: {}", name);
    }

    public static void attachJson(String name, String json) {
        Allure.addAttachment(name, "application/json", json);
        log.debug("JSON attached to Allure report: {}", name);
    }

    public static void attachHtml(String name, String html) {
        Allure.addAttachment(name, "text/html", html);
        log.debug("HTML attached to Allure report: {}", name);
    }

    public static void step(String stepName) {
        Allure.step(stepName);
        log.debug("Allure step: {}", stepName);
    }

    public static void step(String stepName, Status status) {
        Allure.step(stepName, status);
        log.debug("Allure step with status: {} - {}", stepName, status);
    }

    public static void addParameter(String name, String value) {
        Allure.parameter(name, value);
    }

    public static void addLink(String name, String url) {
        Allure.link(name, url);
    }

    public static void addIssue(String name, String url) {
        Allure.issue(name, url);
    }

    public static void addDescription(String description) {
        Allure.description(description);
    }

    public static void addDescriptionHtml(String html) {
        Allure.descriptionHtml(html);
    }
}

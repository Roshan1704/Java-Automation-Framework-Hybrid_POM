package com.automation.api;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.testng.Assert.*;

/**
 * API Utilities - Helper methods for API testing
 */
public class ApiUtils {

    private static final Logger log = LoggerFactory.getLogger(ApiUtils.class);

    private ApiUtils() {
        // Private constructor
    }

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        assertEquals(response.getStatusCode(), expectedStatusCode,
                "Expected status code: " + expectedStatusCode + ", but got: " + response.getStatusCode());
        log.info("Status code validated: {}", expectedStatusCode);
    }

    public static void assertResponseTime(Response response, long maxTime) {
        long responseTime = response.getTime();
        assertTrue(responseTime <= maxTime,
                "Response time " + responseTime + "ms exceeded max time " + maxTime + "ms");
        log.info("Response time validated: {}ms (max: {}ms)", responseTime, maxTime);
    }

    public static void assertJsonPath(Response response, String jsonPath, Object expectedValue) {
        Object actualValue = response.jsonPath().get(jsonPath);
        assertEquals(actualValue, expectedValue,
                "Expected value at '" + jsonPath + "': " + expectedValue + ", but got: " + actualValue);
        log.info("JSON path '{}' validated: {}", jsonPath, expectedValue);
    }

    public static void assertContainsKey(Response response, String key) {
        assertNotNull(response.jsonPath().get(key),
                "Response does not contain key: " + key);
        log.info("Response contains key: {}", key);
    }

    public static void assertResponseNotEmpty(Response response) {
        String body = response.getBody().asString();
        assertFalse(body.isEmpty(), "Response body is empty");
        log.info("Response body is not empty");
    }

    public static <T> T getResponseAs(Response response, Class<T> clazz) {
        return response.as(clazz);
    }

    public static String getJsonValue(Response response, String jsonPath) {
        return response.jsonPath().getString(jsonPath);
    }

    public static int getJsonValueAsInt(Response response, String jsonPath) {
        return response.jsonPath().getInt(jsonPath);
    }

    public static void logResponse(Response response) {
        log.info("Response Status: {}", response.getStatusCode());
        log.info("Response Time: {}ms", response.getTime());
        log.debug("Response Body: {}", response.getBody().asString());
    }
}

package com.automation.tests;

import com.automation.api.ApiClient;
import com.automation.api.ApiUtils;
import com.automation.base.BaseTest;
import com.automation.constants.FrameworkConstants;
import com.automation.driver.DriverManager;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * API Tests - REST API test cases
 */
@Epic("API Testing")
@Feature("REST API")
public class ApiTests {

    private static final Logger log = LoggerFactory.getLogger(ApiTests.class);

    private ApiClient apiClient;

    @BeforeClass
    public void setUpApiClient() {
        apiClient = new ApiClient("https://jsonplaceholder.typicode.com");
    }

    @Test(priority = 1, groups = {FrameworkConstants.API, FrameworkConstants.SMOKE})
    @Story("GET Request")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify GET request returns users")
    public void testGetUsers() {
        Response response = apiClient.get("/users");

        ApiUtils.assertStatusCode(response, 200);
        ApiUtils.assertResponseNotEmpty(response);
        ApiUtils.assertResponseTime(response, 5000);

        log.info("GET users test completed");
    }

    @Test(priority = 2, groups = {FrameworkConstants.API, FrameworkConstants.SMOKE})
    @Story("GET Single Resource")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify GET request returns single user")
    public void testGetSingleUser() {
        Response response = apiClient.get("/users/1");

        ApiUtils.assertStatusCode(response, 200);
        ApiUtils.assertJsonPath(response, "id", 1);
        ApiUtils.assertContainsKey(response, "name");
        ApiUtils.assertContainsKey(response, "email");

        log.info("GET single user test completed");
    }

    @Test(priority = 3, groups = {FrameworkConstants.API, FrameworkConstants.REGRESSION})
    @Story("POST Request")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify POST request creates new user")
    public void testCreateUser() {
        Map<String, Object> user = Map.of(
                "name", "Test User",
                "username", "testuser",
                "email", "test@example.com"
        );

        Response response = apiClient.post("/users", user);

        ApiUtils.assertStatusCode(response, 201);
        ApiUtils.assertContainsKey(response, "id");
        ApiUtils.assertJsonPath(response, "name", "Test User");

        log.info("POST create user test completed");
    }

    @Test(priority = 4, groups = {FrameworkConstants.API, FrameworkConstants.REGRESSION})
    @Story("PUT Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify PUT request updates user")
    public void testUpdateUser() {
        Map<String, Object> updatedUser = Map.of(
                "name", "Updated User",
                "username", "updateduser",
                "email", "updated@example.com"
        );

        Response response = apiClient.put("/users/1", updatedUser);

        ApiUtils.assertStatusCode(response, 200);
        ApiUtils.assertJsonPath(response, "name", "Updated User");

        log.info("PUT update user test completed");
    }

    @Test(priority = 5, groups = {FrameworkConstants.API, FrameworkConstants.REGRESSION})
    @Story("DELETE Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify DELETE request removes user")
    public void testDeleteUser() {
        Response response = apiClient.delete("/users/1");

        ApiUtils.assertStatusCode(response, 200);

        log.info("DELETE user test completed");
    }

    @Test(priority = 6, groups = {FrameworkConstants.API})
    @Story("Query Parameters")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify GET request with query parameters")
    public void testGetWithQueryParams() {
        Response response = apiClient
                .withQueryParam("userId", "1")
                .get("/posts");

        ApiUtils.assertStatusCode(response, 200);
        ApiUtils.assertResponseNotEmpty(response);

        log.info("GET with query params test completed");
    }

    @Test(priority = 7, groups = {FrameworkConstants.API, FrameworkConstants.PERFORMANCE})
    @Story("Performance")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify API response time is within acceptable range")
    public void testApiPerformance() {
        Response response = apiClient.get("/users");

        ApiUtils.assertStatusCode(response, 200);
        ApiUtils.assertResponseTime(response, 2000); // Max 2 seconds

        log.info("API performance test completed. Response time: {}ms", response.getTime());
    }
}

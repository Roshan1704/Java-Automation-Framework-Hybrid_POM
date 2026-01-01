package com.automation.tests;

import com.automation.api.ApiClient;
import com.automation.api.ApiUtils;
import com.automation.constants.FrameworkConstants;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Performance Tests - Basic performance and load tests
 */
@Epic("Performance Testing")
@Feature("Performance Smoke Tests")
public class PerformanceTests {

    private static final Logger log = LoggerFactory.getLogger(PerformanceTests.class);

    private ApiClient apiClient;

    @BeforeClass
    public void setUp() {
        apiClient = new ApiClient("https://jsonplaceholder.typicode.com");
    }

    @Test(priority = 1, groups = {FrameworkConstants.PERFORMANCE})
    @Story("Response Time")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify API response time under normal load")
    public void testResponseTimeUnderNormalLoad() {
        long totalTime = 0;
        int iterations = 10;

        for (int i = 0; i < iterations; i++) {
            Response response = apiClient.get("/users");
            totalTime += response.getTime();
            ApiUtils.assertStatusCode(response, 200);
        }

        long avgTime = totalTime / iterations;
        log.info("Average response time over {} iterations: {}ms", iterations, avgTime);

        Assert.assertTrue(avgTime < 3000, "Average response time should be less than 3 seconds");
    }

    @Test(priority = 2, groups = {FrameworkConstants.PERFORMANCE})
    @Story("Concurrent Requests")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify API handles concurrent requests")
    public void testConcurrentRequests() throws Exception {
        int concurrentUsers = 5;
        ExecutorService executor = Executors.newFixedThreadPool(concurrentUsers);
        List<CompletableFuture<Long>> futures = new ArrayList<>();

        for (int i = 0; i < concurrentUsers; i++) {
            CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> {
                ApiClient client = new ApiClient("https://jsonplaceholder.typicode.com");
                Response response = client.get("/users");
                return response.getTime();
            }, executor);
            futures.add(future);
        }

        long totalTime = 0;
        for (CompletableFuture<Long> future : futures) {
            totalTime += future.get();
        }

        long avgTime = totalTime / concurrentUsers;
        log.info("Average response time for {} concurrent users: {}ms", concurrentUsers, avgTime);

        executor.shutdown();
        Assert.assertTrue(avgTime < 5000, "Average response time should be less than 5 seconds under concurrent load");
    }

    @Test(priority = 3, groups = {FrameworkConstants.PERFORMANCE})
    @Story("Payload Size")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify response payload size is within limits")
    public void testPayloadSize() {
        Response response = apiClient.get("/users");

        int contentLength = response.getBody().asString().length();
        log.info("Response payload size: {} bytes", contentLength);

        Assert.assertTrue(contentLength < 50000, "Response payload should be less than 50KB");
    }
}

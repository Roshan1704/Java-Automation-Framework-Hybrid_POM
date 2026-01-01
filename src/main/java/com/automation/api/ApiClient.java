package com.automation.api;

import com.automation.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * API Client - RestAssured wrapper for API testing
 */
public class ApiClient {

    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);

    private final RequestSpecification requestSpec;

    public ApiClient() {
        String baseUrl = ConfigManager.getInstance().getApiBaseUrl();
        RestAssured.baseURI = baseUrl;

        this.requestSpec = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all();

        log.info("API Client initialized with base URL: {}", baseUrl);
    }

    public ApiClient(String baseUrl) {
        RestAssured.baseURI = baseUrl;

        this.requestSpec = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all();

        log.info("API Client initialized with base URL: {}", baseUrl);
    }

    public ApiClient withHeader(String key, String value) {
        requestSpec.header(key, value);
        return this;
    }

    public ApiClient withHeaders(Map<String, String> headers) {
        requestSpec.headers(headers);
        return this;
    }

    public ApiClient withAuth(String token) {
        requestSpec.header("Authorization", "Bearer " + token);
        return this;
    }

    public ApiClient withBasicAuth(String username, String password) {
        requestSpec.auth().basic(username, password);
        return this;
    }

    public ApiClient withQueryParam(String key, String value) {
        requestSpec.queryParam(key, value);
        return this;
    }

    public ApiClient withQueryParams(Map<String, String> params) {
        requestSpec.queryParams(params);
        return this;
    }

    public ApiClient withPathParam(String key, String value) {
        requestSpec.pathParam(key, value);
        return this;
    }

    public Response get(String endpoint) {
        Response response = requestSpec.get(endpoint);
        log.info("GET {} - Status: {}", endpoint, response.getStatusCode());
        return response;
    }

    public Response post(String endpoint, Object body) {
        Response response = requestSpec.body(body).post(endpoint);
        log.info("POST {} - Status: {}", endpoint, response.getStatusCode());
        return response;
    }

    public Response put(String endpoint, Object body) {
        Response response = requestSpec.body(body).put(endpoint);
        log.info("PUT {} - Status: {}", endpoint, response.getStatusCode());
        return response;
    }

    public Response patch(String endpoint, Object body) {
        Response response = requestSpec.body(body).patch(endpoint);
        log.info("PATCH {} - Status: {}", endpoint, response.getStatusCode());
        return response;
    }

    public Response delete(String endpoint) {
        Response response = requestSpec.delete(endpoint);
        log.info("DELETE {} - Status: {}", endpoint, response.getStatusCode());
        return response;
    }

    public Response postFormData(String endpoint, Map<String, String> formData) {
        Response response = requestSpec
                .contentType(ContentType.URLENC)
                .formParams(formData)
                .post(endpoint);
        log.info("POST (form) {} - Status: {}", endpoint, response.getStatusCode());
        return response;
    }

    public Response uploadFile(String endpoint, String filePath, String fileParamName) {
        Response response = requestSpec
                .contentType(ContentType.MULTIPART)
                .multiPart(fileParamName, new java.io.File(filePath))
                .post(endpoint);
        log.info("File upload {} - Status: {}", endpoint, response.getStatusCode());
        return response;
    }
}

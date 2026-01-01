package com.automation.utils;

import com.automation.constants.FrameworkConstants;
import com.automation.exceptions.FrameworkException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * JSON Reader Utility - Reads test data from JSON files
 */
public class JsonReader {

    private static final Logger log = LoggerFactory.getLogger(JsonReader.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonReader() {
        // Private constructor
    }

    public static Map<String, Object> readJsonAsMap(String fileName) {
        try {
            String filePath = FrameworkConstants.TEST_DATA_PATH + fileName;
            Map<String, Object> data = objectMapper.readValue(
                    new File(filePath),
                    new TypeReference<Map<String, Object>>() {}
            );
            log.info("JSON file loaded as Map: {}", fileName);
            return data;
        } catch (IOException e) {
            throw new FrameworkException("Failed to read JSON file: " + fileName, e);
        }
    }

    public static List<Map<String, Object>> readJsonAsList(String fileName) {
        try {
            String filePath = FrameworkConstants.TEST_DATA_PATH + fileName;
            List<Map<String, Object>> data = objectMapper.readValue(
                    new File(filePath),
                    new TypeReference<List<Map<String, Object>>>() {}
            );
            log.info("JSON file loaded as List: {}", fileName);
            return data;
        } catch (IOException e) {
            throw new FrameworkException("Failed to read JSON file: " + fileName, e);
        }
    }

    public static <T> T readJson(String fileName, Class<T> clazz) {
        try {
            String filePath = FrameworkConstants.TEST_DATA_PATH + fileName;
            T data = objectMapper.readValue(new File(filePath), clazz);
            log.info("JSON file loaded as {}: {}", clazz.getSimpleName(), fileName);
            return data;
        } catch (IOException e) {
            throw new FrameworkException("Failed to read JSON file: " + fileName, e);
        }
    }

    public static JsonNode readJsonAsNode(String fileName) {
        try {
            String filePath = FrameworkConstants.TEST_DATA_PATH + fileName;
            JsonNode node = objectMapper.readTree(new File(filePath));
            log.info("JSON file loaded as JsonNode: {}", fileName);
            return node;
        } catch (IOException e) {
            throw new FrameworkException("Failed to read JSON file: " + fileName, e);
        }
    }

    public static String getValue(String fileName, String key) {
        JsonNode node = readJsonAsNode(fileName);
        String[] keys = key.split("\\.");
        JsonNode current = node;

        for (String k : keys) {
            current = current.get(k);
            if (current == null) {
                return null;
            }
        }

        return current.asText();
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new FrameworkException("Failed to convert object to JSON", e);
        }
    }

    public static String toPrettyJson(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            throw new FrameworkException("Failed to convert object to pretty JSON", e);
        }
    }
}

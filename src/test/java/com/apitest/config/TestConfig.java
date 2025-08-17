package com.apitest.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    private static final Properties properties = new Properties();
    private static final String DEFAULT_CONFIG_FILE = "config.properties";
    private static final String DEFAULT_BASE_URL = "https://jsonplaceholder.typicode.com";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream inputStream = TestConfig.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            System.err.println("Failed to load config file: " + e.getMessage());
        }
    }

   public static String getBaseUrl(String apiName) {
        if (apiName == null || apiName.isEmpty()) {
            return properties.getProperty("base.url", DEFAULT_BASE_URL);
        }
        System.out.println("Fetching base URL for API: " + apiName);
        return properties.getProperty("api." + apiName + ".base.url", DEFAULT_BASE_URL);
    }

    public static int getDefaultTimeout() {
        return Integer.parseInt(properties.getProperty("request.timeout", "10000"));
    }

    public static String getEndpoint(String apiName, String name) {
        return properties.getProperty("api." + apiName + ".endpoint." + name, "/" + name);
    }
}
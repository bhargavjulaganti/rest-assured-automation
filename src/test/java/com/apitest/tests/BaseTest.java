package com.apitest.tests;

import com.apitest.config.TestConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

/**
 * Base test class with common setup for all API tests
 */
public abstract class BaseTest {

    @BeforeSuite
    public void setupSuite() {
        // Global REST-assured configuration
        RestAssured.baseURI = TestConfig.getBaseUrl("jsonplaceholder");
        RestAssured.filters(new AllureRestAssured());
        
        // Configure REST-assured to trust all SSL certificates
        RestAssured.config = RestAssured.config().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation());
        
        // Enable detailed logging
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
    
    @BeforeClass
    public void setupTest() {
        // Additional setup that might be needed before each test class
        System.out.println("Testing API: " + RestAssured.baseURI);
        System.out.println("JsonPlaceholder URL from config: " + TestConfig.getBaseUrl("jsonplaceholder"));
        System.out.println("WireMock URL from config: " + TestConfig.getBaseUrl("wiremock"));
    }
}
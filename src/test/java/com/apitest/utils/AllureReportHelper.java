package com.apitest.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Helper class for enhancing Allure reports with REST-assured logs
 */
public class AllureReportHelper {
    
    /**
     * Adds REST-assured request and response logs to Allure report
     * @param testName The name of the test case
     * @return Object array containing request and response logging filters
     */
    public static Object[] withAllureLogging(String testName) {
        ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
        PrintStream requestPrintStream = new PrintStream(requestOutputStream);
        PrintStream responsePrintStream = new PrintStream(responseOutputStream);
        
        return new Object[]{
            new RequestLoggingFilter(LogDetail.ALL, requestPrintStream),
            new ResponseLoggingFilter(LogDetail.ALL, responsePrintStream),
            (Runnable) () -> {
                try {
                    Allure.attachment(testName + " - HTTP Request", requestOutputStream.toString());
                    requestOutputStream.close();
                    Allure.attachment(testName + " - HTTP Response", responseOutputStream.toString());
                    responseOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    
    /**
     * Attaches JSON content to Allure report
     * @param name Attachment name
     * @param json JSON content
     * @return The same JSON string for method chaining
     */
    @Attachment(value = "{name}", type = "application/json")
    public static String attachJson(String name, String json) {
        return json;
    }
    
    /**
     * Attaches text content to Allure report
     * @param name Attachment name
     * @param text Text content
     * @return The same text string for method chaining
     */
    @Attachment(value = "{name}", type = "text/plain")
    public static String attachText(String name, String text) {
        return text;
    }
}
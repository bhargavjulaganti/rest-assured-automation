package com.apitest.utils;

import com.apitest.config.TestConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import io.restassured.specification.RequestSender;

public class RestAssuredClient {
    
    // Thread-local storage for the bearer token
    private static final ThreadLocal<String> bearerToken = new ThreadLocal<>();
    
    private static final RequestSpecification REQUEST_SPECIFICATION = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .addFilter(new AllureRestAssured())
            .log(LogDetail.ALL)
            .build();
    
    private static final ResponseSpecification RESPONSE_SPECIFICATION = new ResponseSpecBuilder()
            .expectResponseTime(lessThan((long) TestConfig.getDefaultTimeout()), TimeUnit.MILLISECONDS)
            .log(LogDetail.ALL)
            .build();
    
    static {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        // Initialize the bearer token when the class is loaded
        // initBearerToken();
    }
    
    /**
     * Initializes the bearer token for this thread
     */
    public static void initBearerToken() {
        // bearerToken.set(getBearerToken());
    }
    
    public static RequestSpecification getRequestSpec() {
        return given().spec(REQUEST_SPECIFICATION);
    }
    
    /**
     * Gets a request specification with bearer token authentication
     * 
     * @param token The bearer token to add to the request header
     * @return RequestSpecification with the Authorization header set
     */
    public static RequestSpecification getRequestSpecWithToken() {
        return getRequestSpec().header("Authorization", bearerToken.get());
    }
    
    /**
     * Extension of standard given() method that automatically adds the bearer token
     * Use this method instead of RestAssured.given() for authenticated requests
     * 
     * @return RequestSpecification with bearer token added
     */
    public static RequestSpecification givenWithToken() {
        return given().header("Authorization", bearerToken.get());
    }
    
    public static ResponseSpecification getResponseSpec() {
        return RESPONSE_SPECIFICATION;
    }
    
    public static <T> T get(String endpoint, Class<T> responseClass) {
        return getRequestSpec()
                .when()
                .get(endpoint)
                .then()
                .spec(getResponseSpec())
                .extract()
                .as(responseClass);
    }
    
    // public static <T> T get(String endpoint, Map<String, Object> queryParams, Class<T> responseClass) {
    //     return getRequestSpec()
    //             .queryParams(queryParams)
    //             .when()
    //             .get(endpoint)
    //             .then()
    //             .spec(getResponseSpec())
    //             .extract()
    //             .as(responseClass);
    // }
    
    // public static <T> T get(String endpoint, String token, Class<T> responseClass) {
    //     return getRequestSpecWithToken()
    //             .when()
    //             .get(endpoint)
    //             .then()
    //             .spec(getResponseSpec())
    //             .extract()
    //             .as(responseClass);
    // }
    
    // public static <T> T get(String endpoint, Map<String, Object> queryParams, String token, Class<T> responseClass) {
    //     return getRequestSpecWithToken()
    //             .queryParams(queryParams)
    //             .when()
    //             .get(endpoint)
    //             .then()
    //             .spec(getResponseSpec())
    //             .extract()
    //             .as(responseClass);
    // }
    
    // public static <T> T post(String endpoint, Object body, Class<T> responseClass) {
    //     return getRequestSpec()
    //             .body(body)
    //             .when()
    //             .post(endpoint)
    //             .then()
    //             .spec(getResponseSpec())
    //             .extract()
    //             .as(responseClass);
    // }
    
    // public static <T> T post(String endpoint, Object body, String token, Class<T> responseClass) {
    //     return getRequestSpecWithToken()
    //             .body(body)
    //             .when()
    //             .post(endpoint)
    //             .then()
    //             .spec(getResponseSpec())
    //             .extract()
    //             .as(responseClass);
    // }
    
    // public static <T> T put(String endpoint, Object body, Class<T> responseClass) {
    //     return getRequestSpec()
    //             .body(body)
    //             .when()
    //             .put(endpoint)
    //             .then()
    //             .spec(getResponseSpec())
    //             .extract()
    //             .as(responseClass);
    // }
    
    // public static <T> T put(String endpoint, Object body, String token, Class<T> responseClass) {
    //     return getRequestSpecWithToken()
    //             .body(body)
    //             .when()
    //             .put(endpoint)
    //             .then()
    //             .spec(getResponseSpec())
    //             .extract()
    //             .as(responseClass);
    // }
    
    // public static <T> T delete(String endpoint, Class<T> responseClass) {
    //     return getRequestSpec()
    //             .when()
    //             .delete(endpoint)
    //             .then()
    //             .spec(getResponseSpec())
    //             .extract()
    //             .as(responseClass);
    // }
    
    // public static <T> T delete(String endpoint, String token, Class<T> responseClass) {
    //     return getRequestSpecWithToken()
    //             .when()
    //             .delete(endpoint)
    //             .then()
    //             .spec(getResponseSpec())
    //             .extract()
    //             .as(responseClass);
    // }
    
    /**
     * Generates a bearer token using client credentials OAuth flow
     * 
     * @param tokenUrl URL endpoint for token generation
     * @param clientId OAuth client ID
     * @param clientSecret OAuth client secret
     * @param audience API audience value for the token
     * @return Bearer token string
     */
    // public static String getBearerToken() { //String tokenUrl, String clientId, String clientSecret, String audience) {
    //     Map<String, Object> jsonBody = new HashMap<>();
    //     jsonBody.put("grant_type", "client_credentials");
    //     jsonBody.put("client_id", "");
    //     jsonBody.put("client_secret", "");
    //     jsonBody.put("audience", "");
        
    //     Response response = given()
    //             .contentType(ContentType.JSON)
    //             .body(jsonBody)
    //             .when()
    //             .post("")
    //             .then()
    //             .statusCode(200)
    //             .extract()
    //             .response();
        
    //             System.out.println("Token Response: " + response.asString());
                
    //     return "Bearer " + response.jsonPath().getString("access_token");
    // }
}
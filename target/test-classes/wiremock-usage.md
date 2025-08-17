# Using WireMock with REST Assured

This document explains how to use WireMock to intercept and mock REST API calls in your REST Assured tests. There are two different approaches you can use: dedicated WireMock tests or configurable tests that can switch between real APIs and WireMock.

## Overview

WireMock is a library for stubbing and mocking web services. It can be used to mock HTTP-based APIs and provide pre-programmed responses to your tests. This is useful for:

1. Testing your application without calling real external services
2. Creating test scenarios that are difficult to reproduce with real services
3. Testing edge cases and error conditions
4. Making your tests faster and more reliable

## Setup

The project is already configured with WireMock dependencies in the `pom.xml` file. The main components are:

1. `WireMockManager` - A utility class to manage WireMock server instances
2. `WireMockBaseTest` - A base test class for tests that exclusively use WireMock
3. `ConfigurableBaseTest` - A base test class that can switch between real APIs and WireMock
4. Example implementation in `UserApiWireMockTest` (WireMock only)
5. Example implementation in `ConfigurableUserTest` (switchable)

## How to Use WireMock in Your Tests

### Approach 1: WireMock Only Tests

#### 1. Extend the WireMockBaseTest Class

Create a new test class that extends `WireMockBaseTest`:

```java
public class YourApiWireMockTest extends WireMockBaseTest {
    // Your test methods will go here
}
```

### 2. Set Up Request Stubs

Override the `setupStubs()` method to define what requests to intercept and what responses to return:

```java
@Override
protected void setupStubs() {
    // Stub for GET request
    wireMockServer.stubFor(WireMock.get(urlEqualTo("/your-endpoint"))
            .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\"key\": \"value\"}")));
    
    // Stub for POST request
    wireMockServer.stubFor(WireMock.post(urlEqualTo("/your-post-endpoint"))
            .willReturn(aResponse()
                    .withStatus(201)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\"id\": 123, \"status\": \"created\"}")));
}
```

### 3. Write Your Test Methods

Write test methods that use REST Assured to make requests to the WireMock server:

```java
@Test
@Description("Your test description")
public void testYourApi() {
    // Use REST Assured to make a request to the WireMock server
    YourResponseType response = given()
            .contentType(ContentType.JSON)
        .when()
            .get("/your-endpoint")
        .then()
            .statusCode(200)
            .extract()
            .as(YourResponseType.class);
    
    // Make assertions on the response
    assertThat(response.getKey(), equalTo("value"));
    
    // Verify the request was made to WireMock
    wireMockServer.verify(getRequestedFor(urlEqualTo("/your-endpoint")));
}
```

## Advanced WireMock Features

### 1. Using Response Files

You can store response bodies in files under `src/test/resources/__files/`:

```java
.withBodyFile("your-response.json")
```

### 2. Request Matching

Match requests based on URL, headers, body content, etc.:

```java
wireMockServer.stubFor(post(urlEqualTo("/users"))
        .withHeader("Content-Type", containing("application/json"))
        .withRequestBody(matchingJsonPath("$.name", equalTo("John")))
        .willReturn(aResponse()...));
```

### 3. Response Templating

Use response templating to generate dynamic responses:

```java
wireMockServer.stubFor(get(urlPathMatching("/users/\\d+"))
        .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{{request.path.[1]}}"))); // Extracts the user ID from the path
```

### 4. Stateful Behavior

Create stateful interactions for more complex scenarios:

```java
wireMockServer.stubFor(post(urlEqualTo("/users"))
        .inScenario("User CRUD")
        .whenScenarioStateIs(Scenario.STARTED)
        .willReturn(aResponse().withStatus(201))
        .willSetStateTo("USER_CREATED"));

wireMockServer.stubFor(get(urlEqualTo("/users/1"))
        .inScenario("User CRUD")
        .whenScenarioStateIs("USER_CREATED")
        .willReturn(aResponse().withStatus(200)));
```

### Approach 2: Configurable Tests (Real API or WireMock)

If you want to be able to run your tests against either a real API or WireMock, you can use the `ConfigurableBaseTest` class.

#### 1. Extend the ConfigurableBaseTest Class

```java
public class YourConfigurableTest extends ConfigurableBaseTest {
    // Your test methods will go here
}
```

#### 2. Configure the Test Mode

You can configure the test to use either WireMock or a real API in several ways:

a) Constructor with flag:

```java
public YourConfigurableTest() {
    super(true, "apiName"); // true = use WireMock, false = use real API
}
```

b) System property:

```java
public YourConfigurableTest() {
    this(Boolean.parseBoolean(System.getProperty("use.wiremock", "false")));
}

public YourConfigurableTest(boolean useMock) {
    super(useMock, "apiName");
}
```

#### 3. Set Up Stubs (Only Used When WireMock is Enabled)

```java
@Override
protected void setupStubs() {
    wireMockServer.stubFor(WireMock.get(urlEqualTo("/your-endpoint"))
            .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\"key\": \"value\"}"));
}
```

#### 4. Write Test Methods

Your test methods will work the same way regardless of whether you're using WireMock or the real API. You can add conditional logic based on the `useWireMock` flag if needed:

```java
@Test
public void testYourApi() {
    // Test code works with both real API and WireMock
    YourResponseType response = given()
            .contentType(ContentType.JSON)
        .when()
            .get("/your-endpoint")
        .then()
            .statusCode(200)
            .extract()
            .as(YourResponseType.class);
    
    // Common assertions
    assertThat(response.getSomeValue(), equalTo("expected"));
    
    // WireMock-specific verifications
    if (useWireMock) {
        wireMockServer.verify(getRequestedFor(urlEqualTo("/your-endpoint")));
    }
}
```

#### 5. Running Configurable Tests

You can run the tests with WireMock by setting the system property:

```bash
mvn test -Duse.wiremock=true
```

Or run against the real API by omitting the property or setting it to false:

```bash
mvn test -Duse.wiremock=false
```

## Examples

See the following examples in this project:

- `UserApiWireMockTest.java` - A WireMock-only test using `WireMockBaseTest`
- `ConfigurableUserTest.java` - A configurable test that can use either WireMock or real API

## Resources

- [WireMock Documentation](http://wiremock.org/docs/)
- [REST Assured Documentation](https://rest-assured.io/)
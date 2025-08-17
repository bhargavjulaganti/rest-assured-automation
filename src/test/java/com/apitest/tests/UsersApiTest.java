package com.apitest.tests;

import com.apitest.config.TestConfig;
import com.apitest.models.User;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Feature("Users API")
public class UsersApiTest extends BaseTest {

    private final String BASE_URL = TestConfig.getBaseUrl("jsonplaceholder");
    private final String USERS_ENDPOINT = TestConfig.getEndpoint("jsonplaceholder", "users");

    @Test
    @Description("Verify that the users endpoint returns a list of users")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllUsers() {
        System.out.println("Base URL is ***************: " + BASE_URL);
        // Test getting all users
        List<User> users = given()
            .baseUri("https://jsonplaceholder.typicode.com")
            .contentType(ContentType.JSON)
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract()
            .jsonPath()
            .getList("", User.class);

        // Print each user
        for (User user : users) {
            System.out.println(user.getId());
        }

        // Verify that we have users in the response
        assertThat(users, hasSize(greaterThan(0)));
        assertThat(users.get(0).getId(), greaterThan(0));
        // assertThat(users.get(0).getName(), notNullValue());
    }
}
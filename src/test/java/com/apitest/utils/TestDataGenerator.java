package com.apitest.utils;

// import com.apitest.models.Post;
import com.apitest.models.User;

import java.util.Random;
import java.util.UUID;

/**
 * Utility class to generate test data for API tests
 */
public class TestDataGenerator {
    private static final Random random = new Random();
    
    /**
     * Generate a random user with test data
     * @return User object with random data
     */
    // public static User generateRandomUser() {
    //     int id = random.nextInt(1000) + 1;
    //     // return User.builder()
    //     //         .id(id)
    //     //         .name("Test User " + id)
    //     //         .username("testuser" + id)
    //     //         .email("user" + id + "@test.com")
    //     //         .phone("555-" + (1000 + random.nextInt(9000)))
    //     //         .website("testuser" + id + ".com")
    //     //         .build();
    // }
    
    /**
     * Generate a random post with test data
     * @param userId The user ID to associate with the post
     * @return Post object with random data
     */
    // public static Post generateRandomPost(int userId) {
    //     int id = random.nextInt(1000) + 1;
    //     return Post.builder()
    //             .id(id)
    //             .userId(userId)
    //             .title("Test Post " + UUID.randomUUID().toString().substring(0, 8))
    //             .body("This is a test post body generated for testing purposes. " +
    //                   "It contains random text for API testing with REST-assured. " + 
    //                   UUID.randomUUID().toString())
    //             .build();
    // }
}
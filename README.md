# REST-assured API Testing Framework

A robust framework for testing RESTful APIs using REST-assured, TestNG, and Allure reporting.

## Project Structure

```
rest-assured/
├── src/
│   ├── main/java/            # Main source code (for custom libraries if needed)
│   └── test/
│       ├── java/com/apitest/
│       │   ├── config/       # Test configuration
│       │   ├── models/       # POJO classes for request/response
│       │   ├── tests/        # Test classes
│       │   └── utils/        # Utility classes
│       └── resources/        # Test resources and configuration files
└── pom.xml                   # Maven configuration
```

## Features

- REST-assured for API testing
- TestNG as the test runner
- Allure reporting for detailed test reports
- Lombok for reducing boilerplate code
- Jackson for JSON serialization/deserialization
- Modular design with separation of concerns

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- Allure command-line tool (optional, for report generation)

### Running Tests

Run all tests:

```bash
mvn clean test
```

Run specific test classes:

```bash
mvn clean test -Dtest=UsersApiTest
```

### Generating Reports

Generate and open Allure reports:

```bash
mvn allure:report
mvn allure:serve
```

## API Test Coverage

- Users API
  - Get all users
  - Get user by ID
  - Create new user
  - Update existing user
  - Delete user

- Posts API
  - Get all posts
  - Get post by ID
  - Get posts by user ID
  - Create new post
  - Update existing post
  - Delete post

## Customizing the Framework

### Adding New Models

1. Create a new POJO class in the `models` package
2. Add Lombok annotations for convenience
3. Add Jackson annotations for JSON serialization/deserialization

### Adding New Tests

1. Create a new test class extending `BaseTest`
2. Add test methods with TestNG and Allure annotations
3. Use REST-assured for API interactions
4. Add assertions for validation

### Changing Configuration

Modify the `config.properties` file to:
- Change the base URL
- Add new endpoints
- Configure timeouts and other settings

## Best Practices Implemented

- Base test class for common setup
- Separation of test data from test logic
- Proper error handling and logging
- Clean and maintainable test code
- Detailed test reporting
- Type-safe request/response handling with POJOs
- Descriptive test names and documentation
# Testing Guide for Logix API

This document provides a comprehensive guide for testing the Logix API project.

## Test Structure

The project follows a three-layer testing approach:

1. **Unit Tests** (`src/test/kotlin/com/logix/service/`)
   - Tests individual components in isolation
   - Uses MockK for mocking dependencies
   - Fast execution and focused testing
   - Example: `BookServiceTest.kt`

2. **Controller Tests** (`src/test/kotlin/com/logix/controller/`)
   - Tests REST endpoints
   - Uses MockMvc for HTTP request simulation
   - Validates responses and status codes
   - Example: `BookControllerTest.kt`

3. **Integration Tests** (`src/test/kotlin/com/logix/integration/`)
   - Tests complete application flow
   - Uses TestContainers for database testing
   - Tests data persistence and API responses
   - Example: `BookIntegrationTest.kt`

## Test Dependencies

Key testing dependencies in `build.gradle.kts`:

```kotlin
// Core testing
testImplementation("org.springframework.boot:spring-boot-starter-test")
testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

// Mocking
testImplementation("io.mockk:mockk:1.13.9")

// Integration testing
testImplementation("org.testcontainers:testcontainers:1.19.3")
testImplementation("org.testcontainers:junit-jupiter:1.19.3")
```

## Running Tests

### Basic Test Execution
```bash
./gradlew test
```

### Detailed Test Execution
```bash
./gradlew test --info
```

### Running Specific Test Categories
```bash
# Unit tests only
./gradlew test --tests "com.logix.service.*"

# Controller tests only
./gradlew test --tests "com.logix.controller.*"

# Integration tests only
./gradlew test --tests "com.logix.integration.*"
```

## Test Examples

### Unit Test Example
```kotlin
@Test
fun `findById should return book when it exists`() {
    // Given
    val bookId = 1L
    val book = Book(...)
    every { bookRepository.findByEntityId(bookId) } returns book

    // When
    val result = bookService.findById(bookId)

    // Then
    assertNotNull(result)
    assertEquals(bookId, result.id)
}
```

### Controller Test Example
```kotlin
@Test
fun `create should create new book`() {
    // Given
    val bookDTO = BookDTO(...)

    // When/Then
    mockMvc.perform(
        post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookDTO))
    )
        .andExpect(status().isCreated)
        .andExpect(jsonPath("$.success").value(true))
}
```

### Integration Test Example
```kotlin
@Test
fun `should create and retrieve book`() {
    // Given
    val bookDTO = BookDTO(...)

    // When/Then
    mockMvc.perform(
        post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookDTO))
    )
        .andExpect(status().isCreated)
}
```

## Best Practices

1. **Test Organization**
   - Clear separation between test types
   - One test class per component
   - Descriptive test names

2. **Test Structure**
   - Follow Given/When/Then pattern
   - Isolated test cases
   - Clear assertions

3. **Test Coverage**
   - Test all CRUD operations
   - Include success and failure scenarios
   - Validate data integrity
   - Test API responses

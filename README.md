# Logix API

## English

### Overview
Logix API is a modern RESTful API built with Kotlin and Spring Boot. It provides a robust backend solution with JPA support and OpenAPI documentation.

### Technologies
- Kotlin 1.9.25
- Spring Boot 3.5.0
- Java 21
- Spring Data JPA
- H2 Database
- SpringDoc OpenAPI
- Gradle

### Features
- RESTful API architecture
- JPA-based data persistence
- OpenAPI documentation
- H2 in-memory database
- JUnit 5 testing framework

### Prerequisites
- JDK 21 or higher
- Gradle

### Getting Started

1. Clone the repository:
```bash
git clone [repository-url]
```

2. Navigate to the project directory:
```bash
cd logix-api
```

3. Build the project:
```bash
./gradlew build
```

4. Run the application:
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### API Documentation
Once the application is running, you can access the OpenAPI documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### Testing
Run the tests using:
```bash
./gradlew test
```

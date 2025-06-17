# Logix API

### Overview
API created to start the study of Kotlin with Spring using the REST architecture. This repository serves as a learning project to explore various Spring Boot features and best practices.

### Technologies
- Kotlin
- Spring Boot
- Spring Data JPA
- Spring Security
- H2 Database
- SpringDoc OpenAPI
- Gradle

### Features
- RESTful API architecture
- JPA-based data persistence
- OpenAPI documentation with Swagger UI
- Spring Security for authentication and authorization
- H2 in-memory database
- JUnit 5 testing framework

### Security Implementation
Spring Security has been implemented to:
- Secure all endpoints with JWT authentication
- Implement role-based access control (RBAC)
- Provide secure password encryption
- Handle user authentication and authorization

### API Documentation
The project uses SpringDoc OpenAPI (Swagger) with the following configurations:
- Custom path: `/swagger-ui.html`
- API Documentation path: `/api-docs`
- Enabled request duration display
- Method-based operation sorting
- Alpha-based tag sorting
- Collapsed documentation by default
- Enabled "Try it out" feature
- Enabled filtering
- Added "Authorize" button for JWT authentication

### Configuration
The project uses two configuration files:
1. `application.yaml` - Contains default configurations
2. `application-dev.yml` - Contains development-specific configurations (not committed to repository)

To set up the development environment:
1. Create a new file `src/main/resources/application-dev.yml`
2. Copy the example configuration from `application-dev.yml.example`
3. Update the values according to your development environment

### Prerequisites
- JDK 21 or higher
- Gradle

### Getting Started

1. Clone the repository:
```bash
git clone https://github.com/jeffersontavaresdm/logix.git
```

2. Navigate to the project directory:
```bash
cd logix
```

3. Create and configure application-dev.yml:
```bash
cp src/main/resources/application-dev.yml.example src/main/resources/application-dev.yml
```

4. Build the project:
```bash
./gradlew build
```

5. Run the application:
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### API Documentation
Once the application is running, you can access the OpenAPI documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

### Testing
For detailed information about testing, please refer to [TESTING.md](TESTING.md)

Run the tests using:
```bash
./gradlew test
```

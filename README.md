# Logix API

[English](#english) | [Português](#português)

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

## Português

### Visão Geral
Logix API é uma API RESTful moderna construída com Kotlin e Spring Boot. Fornece uma solução robusta de backend com suporte a JPA e documentação OpenAPI.

### Tecnologias
- Kotlin 1.9.25
- Spring Boot 3.5.0
- Java 21
- Spring Data JPA
- Banco de Dados H2
- SpringDoc OpenAPI
- Gradle

### Funcionalidades
- Arquitetura RESTful
- Persistência de dados com JPA
- Documentação OpenAPI
- Banco de dados H2 em memória
- Framework de testes JUnit 5

### Pré-requisitos
- JDK 21 ou superior
- Gradle

### Como Começar

1. Clone o repositório:
```bash
git clone [url-do-repositorio]
```

2. Navegue até o diretório do projeto:
```bash
cd logix-api
```

3. Construa o projeto:
```bash
./gradlew build
```

4. Execute a aplicação:
```bash
./gradlew bootRun
```

A aplicação iniciará em `http://localhost:8080`

### Documentação da API
Quando a aplicação estiver em execução, você pode acessar a documentação OpenAPI em:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

### Testes
Execute os testes usando:
```bash
./gradlew test
``` 
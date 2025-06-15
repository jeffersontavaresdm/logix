plugins {
  id("org.springframework.boot") version "3.2.3"
  id("io.spring.dependency-management") version "1.1.4"
  kotlin("jvm") version "1.9.22"
  kotlin("plugin.spring") version "1.9.22"
  id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
  kotlin("plugin.jpa") version "1.9.22"
}

group = "com.logix"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
  mavenCentral()
}

dependencies {
  // Spring Boot Starters
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

  // Kotlin Support
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")

  // Database
  runtimeOnly("org.postgresql:postgresql")
  implementation("org.flywaydb:flyway-core")
  runtimeOnly("com.h2database:h2")

  // API Documentation
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

  // Testing
  testImplementation(kotlin("test"))
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(module = "mockito-core")
  }
  testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.2")
  testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
  testImplementation("io.mockk:mockk:1.13.9")
  testImplementation("org.springframework.security:spring-security-test")
  testImplementation("org.testcontainers:testcontainers:1.19.7")
  testImplementation("org.testcontainers:junit-jupiter:1.19.7")
  testImplementation("org.testcontainers:postgresql:1.19.7")

  // JWT
  implementation("io.jsonwebtoken:jjwt-api:0.11.5")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
    showStandardStreams = true
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs += "-Xjsr305=strict"
    jvmTarget = "17"
  }
}

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

// Configuração para limpar o diretório de relatórios antes de cada execução
tasks.clean {
  doLast {
    delete("${project.buildDir}/reports/tests")
  }
}

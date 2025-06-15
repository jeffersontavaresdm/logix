server:
  port: ${PORT:8080}

spring:
  application:
    name: logix

  datasource:
    url: jdbc:h2:mem:logix;
    driver-class-name: org.h2.Driver
    username: adm
    password:

  h2:
    console:
      enabled: true
      path: /h2-console

  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

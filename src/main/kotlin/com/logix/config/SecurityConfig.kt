package com.logix.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
      .csrf { it.disable() }
      .authorizeHttpRequests { auth ->
        auth.requestMatchers(
          "/swagger-ui/**",
          "/swagger-ui.html",
          "/v3/api-docs/**",
          "/api-docs/**",
          "/h2-console/**",
          "/webjars/**"
        ).permitAll()
          .requestMatchers("/api/**").permitAll()
          .anyRequest().authenticated()
      }
      .headers { it.disable() }

    return http.build()
  }
} 
package com.logix.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {

  private val logger = LoggerFactory.getLogger(SecurityConfig::class.java)

  @Value("\${SPRING_SECURITY_USER_NAME:adm}")
  private lateinit var username: String

  @Value("\${SPRING_SECURITY_USER_PASSWORD:adm123}")
  private lateinit var password: String

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

  @Bean
  fun userDetailsService(): UserDetailsService {
    logger.info("Using credentials - Username: $username")
    val userDetails = User
      .builder()
      .username(username)
      .password(passwordEncoder().encode(password))
      .roles("USER")
      .build()

    return InMemoryUserDetailsManager(userDetails)
  }

  @Bean
  fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
    return config.authenticationManager
  }

  @Bean
  fun securityFilterChain(
    http: HttpSecurity,
    jwtAuthenticationFilter: JwtAuthenticationFilter
  ): SecurityFilterChain {
    http
      .csrf { it.disable() }
      .authorizeHttpRequests { auth ->
        auth
          .requestMatchers("/api/auth/login").permitAll()
          .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()
          .requestMatchers(HttpMethod.POST, "/api/books/**").authenticated()
          .requestMatchers(HttpMethod.PUT, "/api/books/**").authenticated()
          .requestMatchers(HttpMethod.DELETE, "/api/books/**").authenticated()
          .requestMatchers(
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/h2-console/**",
            "/webjars/**"
          ).permitAll()
          .anyRequest().authenticated()
      }
      .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
      .headers { it.disable() }
      .exceptionHandling { exceptions ->
        exceptions
          .authenticationEntryPoint { request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException ->
            if (request.requestURI.startsWith("/api/")) {
              response.status = HttpStatus.UNAUTHORIZED.value()
              response.contentType = "application/json"
              response.writer.write("""{"error": "Unauthorized", "message": "Authentication required"}""")
            } else {
              response.sendRedirect("/login")
            }
          }
      }.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

    return http.build()
  }
} 
package com.logix.controller

import com.logix.dto.AuthRequest
import com.logix.service.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<Map<String, String>> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password
            )
        )

        val userDetails = userDetailsService.loadUserByUsername(request.username)
        val token = jwtService.generateToken(userDetails)

        return ResponseEntity.ok(mapOf("token" to token))
    }
} 
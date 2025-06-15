package com.logix.dto

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class ApiResponse<T>(val description: String?, val content: T?) {
  companion object {
    fun <T> success(description: String?, content: T): ApiResponse<T> = ApiResponse(description, content)
    fun <T> error(description: String?): ApiResponse<T> = ApiResponse(description, null)
  }
}

fun <T> ApiResponse<T>.asResponse(status: HttpStatus): ResponseEntity<ApiResponse<T>> = ResponseEntity
  .status(status)
  .body(this)
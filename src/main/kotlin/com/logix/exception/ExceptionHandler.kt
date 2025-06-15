package com.logix.exception

import com.logix.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(BaseException::class)
  fun handleBaseException(exception: BaseException): ResponseEntity<ErrorResponse> {
    val status = when (exception) {
      is BookNotFoundException -> HttpStatus.NOT_FOUND
      is ValidationException -> HttpStatus.BAD_REQUEST
      else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    val error = ErrorResponse(
      code = exception.code,
      message = exception.message,
      status = status.value()
    )

    return ResponseEntity.status(status).body(error)
  }

  @ExceptionHandler(Exception::class)
  fun handleGeneric(ex: Exception): ResponseEntity<ErrorResponse> {
    val error = ErrorResponse(
      code = "INTERNAL_ERROR",
      message = ex.message ?: "Unexpected Error",
      status = HttpStatus.INTERNAL_SERVER_ERROR.value()
    )

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
  }
}

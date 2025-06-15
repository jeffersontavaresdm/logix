package com.logix.exception

class ValidationException(details: String) : BaseException(
  code = "VALIDATION_ERROR",
  message = "Invalid input: $details"
)
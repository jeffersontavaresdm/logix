package com.logix.dto

import java.time.OffsetDateTime

data class ErrorResponse(
  val code: String,
  val message: String,
  val status: Int,
  val timestamp: String = OffsetDateTime.now().toString()
)

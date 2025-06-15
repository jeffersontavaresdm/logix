package com.logix.exception

open class BaseException(
  val code: String,
  override val message: String,
  override val cause: Throwable? = null
) : RuntimeException(message, cause)